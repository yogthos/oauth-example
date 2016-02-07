(ns oauth-example.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[oauth-example started successfully]=-"))
   :middleware identity})
