(ns oauth-example.routes.home
  (:require [oauth-example.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok found]]
            [clojure.java.io :as io]
            [oauth-example.routes.twitter-oauth :as tw]
            [clojure.tools.logging :as log]))

(defn home-page [opts]
  (layout/render "home.html" opts))

(defn about-page []
  (layout/render "about.html"))

(defn twitter-init
  "Initiates the Twitter OAuth"
  [request]
  (-> (tw/fetch-request-token request)
      :oauth_token
      tw/auth-redirect-uri
      found))

(defn twitter-callback
  "Handles the callback from Twitter."
  [request_token {:keys [session]}]
  ; oauth request was denied by user
  (if (:denied request_token)
    (-> (found "/")
        (assoc :flash {:denied true}))
    ; fetch the request token and do anything else you wanna do if not denied.
    (let [{:keys [user_id screen_name]} (tw/fetch-access-token request_token)]
      (log/info "successfully authenticated as" user_id screen_name)
      (-> (found "/")
          (assoc :session
            (assoc session :user-id user_id :screen-name screen_name))))))

(defroutes home-routes
  (GET "/" {:keys [flash session]} (home-page (or flash session)))
  (GET "/about" [] (about-page))
  (GET "/oauth/twitter-init" req (twitter-init req))
  (GET "/oauth/twitter-callback" [& req_token :as req] (twitter-callback req_token req)))

