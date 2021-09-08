(ns com.github.zjjfly.sequences.seq
  (:require [clojure.java.jdbc :as jdbc]
            [com.github.zjjfly.sequences.config :as config]
            [taoensso.nippy :as nippy]))

(def db-spec
  (config/db-spec))

(defn create-seq
  [^String seq-name]
  (jdbc/execute! db-spec (str "create sequence " seq-name)))

(defn list-seq
  []
  (jdbc/query db-spec
              ["select sequencename,COALESCE(last_value,start_value) as last_value from pg_sequences;"]))

(defn next-val
  [seq-name]
  (->
   (jdbc/query db-spec ["select nextval(?)" seq-name])
   first
   (:nextval)))

(defn drop-seq
  [seq-name]
  (jdbc/execute! db-spec (str "drop sequence " seq-name)))

(nippy/thaw (nippy/freeze [1 2 3]))

;(create-seq "orders")
