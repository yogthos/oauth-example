(ns oauth-example.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [oauth-example.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[oauth-example started successfully using the development profile]=-"))
   :middleware wrap-dev})
