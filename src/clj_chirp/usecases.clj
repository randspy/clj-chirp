(ns clj-chirp.usecases
  (:require [clj-chirp.content :as content]
            [clj-chirp.formatter :as formatter]
            [clj-chirp.reading :as reading]
            [clj-chirp.posting :as posting]
            [clj-chirp.time :as time]))


(defn when-posting [posts user-input timestamp]
  (if (.contains user-input "->")
    (let [user-input-after-split (content/split-user-from-content user-input "->")
          user-posts (posting/post @posts user-input-after-split timestamp)]
      (reset! posts user-posts))
    nil))


(defn one-post-formatted [post timestamp]
  (let [time-diff (time/time-diffrence (:timestamp post) timestamp)]
    (str (formatter/format-post {:content   (:content post)
                                 :time-diff (:time-diff time-diff)
                                 :time-unit (:time-unit time-diff)})
         "\n")))

(defn when-reading [posts user-input timestamp]
  (let [all-user-posts (reading/read-user-posts @posts user-input)]
    (if (seq all-user-posts)
      (let [user-posts (:posts all-user-posts)]
        (print (reduce str (map #(one-post-formatted % timestamp) user-posts)))))))

(defn usecase-execution [posts user-input timestamp]
  (or (when-posting posts user-input timestamp)
      (when-reading posts user-input timestamp)))
