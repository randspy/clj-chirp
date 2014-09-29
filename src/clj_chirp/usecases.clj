(ns clj-chirp.usecases
  (:require [clj-chirp.content :as content]
            [clj-chirp.following :as following]
            [clj-chirp.formatter :as formatter]
            [clj-chirp.reading :as reading]
            [clj-chirp.posting :as posting]
            [clj-chirp.wall :as wall]
            [clj-chirp.time :as time]))

(defn- one-formatted-post [post timestamp formatter]
  (let [time-diff (time/time-diffrence (:timestamp post) timestamp)]
    (str (formatter (merge post time-diff)) "\n")))

(defn- formatted-posts [posts timestamp formatter]
  (let [sorted-posts (reverse (sort-by :timestamp posts))]
    (reduce str (map #(one-formatted-post % timestamp formatter) sorted-posts))))

(defn- token-with-position-in-text [text token]
  (let [position (.indexOf text token)]
    (if (< -1 position)
      {:token token :token-position position}
      nil)))

(defn- first-marker-in-text? [text token]
  (let [tokens-with-their-position
        (remove nil?
                (map #(token-with-position-in-text text %) ["->" "follows" "wall"]))]
    (= token (:token (first (sort-by :token-position tokens-with-their-position))))))

(defn- when-posting [all-users-posts user-input timestamp]
  (if (first-marker-in-text? user-input "->")
    (let [user-input-after-split (content/split-user-name-from-content user-input "->")
          user-posts (posting/post @all-users-posts user-input-after-split timestamp)]
      (reset! all-users-posts user-posts))
    nil))


(defn- when-following [all-users-posts user-input]
  (if (first-marker-in-text? user-input "follows")
    (let [user-input-after-split (content/split-user-name-from-content user-input "follows")
          user-posts (following/follows @all-users-posts user-input-after-split)]
      (reset! all-users-posts user-posts))
    nil))

(defn- when-wall [all-users-posts user-input timestamp]
  (if (first-marker-in-text? user-input "wall")
    (let [user-name (:user-name (content/split-user-name-from-content user-input "wall"))
          user-posts-with-follewed-ones (wall/show-wall @all-users-posts user-name)]
      (if (seq user-posts-with-follewed-ones)
        (print (formatted-posts
                 user-posts-with-follewed-ones timestamp formatter/format-post-with-user-name))))
    nil))

(defn- when-reading [all-users-posts user-input timestamp]
  (let [user-posts (reading/read-user-posts @all-users-posts user-input)]
    (if (seq user-posts)
      (print (formatted-posts
               (:posts-values user-posts) timestamp formatter/format-post)))))

(defn usecase-execution [all-users-posts user-input timestamp]
  (or (when-posting all-users-posts user-input timestamp)
      (when-following all-users-posts user-input)
      (when-wall all-users-posts user-input timestamp)
      (when-reading all-users-posts user-input timestamp)))
