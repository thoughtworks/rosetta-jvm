(ns rosettajvm.deployment.routing
  (:require [clojure.tools.logging :as log])
  (:use [compojure.core]
        [compojure.route]
        [compojure.handler]
        [ring.middleware.params]
        [cheshire.core :as json]
        [rosettajvm.deployment.health]
        [rosettajvm.deployment.utilities]
        [rosettajvm.deployment.response]))

(defn status []
  (success {:status "UP"
            :uptime {:value (uptime)
                     :units :millis}}))

(defn deploy [{:keys [commit] :as payload}]
  (log/info "Received deployment request with payload: \n" (hashmap-to-string payload))
  (success {:commit commit}))

(defn unsupported []
  (error {:error "UNSUPPORTED REQUEST"}))

(defroutes application
  (GET  "/application/status" []               (status))
  (POST "/deploy"             {params :params} (deploy (json/parse-string (:payload params) true)))
  (not-found (unsupported)))

(def application-handler
  (-> application
    api
    wrap-params))