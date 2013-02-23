(ns rosettajvm.deployment.response
  (:use [cheshire.core :as json]))

(defn response [status body]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defn success [body]
  (response 200 body))

(defn error [body]
  (response 400 body))