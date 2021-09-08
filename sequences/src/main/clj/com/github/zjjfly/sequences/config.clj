(ns com.github.zjjfly.sequences.config
  (:require [clojure.java.io :refer [resource]])
  (:require [clojure.edn :as edn]))

(def default-config
  {:db-spec {:dbtype "postgresql"
             :dbname "sequences"}})

(def conf (delay
           (if-let [url (resource "config.edn")]
             (merge default-config
                    (-> url
                        slurp
                        (edn/read-string))))))

(defn db-spec []
  (:db-spec @conf))
