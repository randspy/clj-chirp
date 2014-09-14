(ns clj-chirp.usecases
  (:require [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]))


(defn when-posting [posts user-input timestamp]
  (if (.contains user-input "->")
    (let [user-input-after-split (content/split-user-from-content user-input "->")
          user-posts (posting/post @posts user-input-after-split timestamp)]
      (reset! posts user-posts))
    nil))

(defn usecase-execution [posts user-input timestamp]
  (when-posting posts user-input timestamp))
