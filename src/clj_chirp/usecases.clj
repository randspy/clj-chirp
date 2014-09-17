(ns clj-chirp.usecases
  (:require [clj-chirp.content :as content]
            [clj-chirp.following :as following]
            [clj-chirp.formatter :as formatter]
            [clj-chirp.reading :as reading]
            [clj-chirp.posting :as posting]
            [clj-chirp.wall :as wall]
            [clj-chirp.time :as time]))

(defn- one-post-formatted [post timestamp formatter]
  (let [time-diff (time/time-diffrence (:timestamp post) timestamp)]
    (str (formatter (merge post time-diff)) "\n")))

(defn- user-posts-in-string [all-user-posts timestamp formatter]
  (let [sorted-posts (reverse (sort-by :timestamp all-user-posts))]
    (reduce str (map #(one-post-formatted % timestamp formatter) sorted-posts))))

(defn- when-posting [posts user-input timestamp]
  (if (.contains user-input "->")
    (let [user-input-after-split (content/split-user-name-from-content user-input "->")
          user-posts (posting/post @posts user-input-after-split timestamp)]
      (reset! posts user-posts))
    nil))

(defn- when-following [posts user-input]
  (if (.contains user-input "follows")
    (let [user-input-after-split (content/split-user-name-from-content user-input "follows")
          user-posts (following/follows @posts user-input-after-split)]
      (reset! posts user-posts))
    nil))

(defn- when-wall [posts user-input timestamp]
  (if (.contains user-input "wall")
    (let [user-name (:user-name (content/split-user-name-from-content user-input "wall"))
          user-posts-with-follewed-ones (wall/show-wall @posts user-name)]
      (if (seq user-posts-with-follewed-ones)
        (print (user-posts-in-string
                 user-posts-with-follewed-ones timestamp formatter/format-post-with-user-name))))
    nil))

(defn- when-reading [posts user-input timestamp]
  (let [all-user-posts (reading/read-user-posts @posts user-input)]
    (if (seq all-user-posts)
      (print (user-posts-in-string
               (:posts all-user-posts) timestamp formatter/format-post)))))

(defn usecase-execution [posts user-input timestamp]
  (or (when-posting posts user-input timestamp)
      (when-following posts user-input)
      (when-wall posts user-input timestamp)
      (when-reading posts user-input timestamp)))
