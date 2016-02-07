(ns oauth-example.routes.home
  (:require [oauth-example.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok found]]
            [clojure.java.io :as io]
            [oauth-example.routes.twitter-oauth :as tw]))

(defn home-page [& [opts]]
  (layout/render
    "home.html" opts))

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
  [request_token {session :session}]
  (if (:denied request_token)
    (found "/") ; oauth request was denied by user
    ; fetch the request token and do anything else you wanna do if not denied.
    (home-page {:user (tw/fetch-access-token request_token)})))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/denied" [] (home-page {:denied true}))
  (GET "/about" [] (about-page))
  (GET "/oauth/twitter-init" req (twitter-init req))
  (GET "/oauth/twitter-callback" [& req_token :as req] (twitter-callback req_token req)))

