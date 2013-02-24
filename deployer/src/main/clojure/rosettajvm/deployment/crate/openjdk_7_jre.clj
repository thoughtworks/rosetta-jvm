(ns rosettajvm.deployment.crate.openjdk-7-jre
  (:use [pallet.crate.java :as java-crate]))

(def with-openjdk-7-jre
  (java-crate/java {:vendor :openjdk
                    :version [7]
                    :components #{:jre }}))
