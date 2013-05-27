(ns rosettajvm.deployment.routing
  (:require [clojure.tools.logging :as logger])
  (:use [compojure.core]
        [compojure.route]
        [compojure.handler]
        [ring.middleware.params]
        [cheshire.core :as json]
        [rosettajvm.deployment.health]
        [rosettajvm.deployment.utilities]
        [rosettajvm.deployment.response]
        [rosettajvm.deployment.provision]
        [pallet.algo.fsmop :only [wait-for]]))

(defn status []
  (success {:status "UP"
            :uptime {:value (uptime)
                     :units :millis}}))

(defn deploy [{:keys [commit] :as payload}]
  (logger/info "Received deployment request with payload: \n" (hashmap-to-string payload))
  (wait-for (bring-node-down-on :aws))
  (bring-node-up-on :aws commit)
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