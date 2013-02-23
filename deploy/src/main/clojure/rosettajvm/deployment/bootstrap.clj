(ns rosettajvm.deployment.bootstrap
  (:use [rosettajvm.deployment.routing]
        [rosettajvm.deployment.health]
        [ring.adapter.jetty]))

(defn bootstrap [options]
  (register-boot-time)
  (run-jetty application-handler (select-keys options [:port])))




