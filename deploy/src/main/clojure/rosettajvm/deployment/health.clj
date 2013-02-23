(ns rosettajvm.deployment.health
  (:require [clojure.tools.logging :as log]
            [clj-time.core :as time]
            [clj-time.coerce :as time-conversions]
            [clj-time.format :as time-formatters]))

(def ^{:private true} boot-time (atom nil))

(defn register-boot-time []
  (log/info "Registering boot time.")
  (swap! boot-time (constantly (System/currentTimeMillis))))

(defn uptime []
  (- (System/currentTimeMillis) @boot-time))