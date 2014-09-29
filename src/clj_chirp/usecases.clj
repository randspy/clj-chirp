(ns clj-chirp.usecases
  (:require [clj-chirp.content :as content]
            [clj-chirp.following :as following]
            [clj-chirp.formatter :as formatter]
            [clj-chirp.markers :as markers]
            [clj-chirp.reading :as reading]
            [clj-chirp.posting :as posting]
            [clj-chirp.wall :as wall]
            [clj-chirp.time :as time]))

(defn- token-with-position-in-text [text token]
  (let [position (.indexOf text token)]
    (if (< -1 position)
      {:token token :token-position position}
      nil)))

(defn- first-marker-in-text? [text token]
  (let [tokens-with-their-position
        (remove nil?
                (map #(token-with-position-in-text text %) markers/all-markers))]
    (= token (:token (first (sort-by :token-position tokens-with-their-position))))))

(defn- when-posting [all-users-posts user-input timestamp]
  (if (first-marker-in-text? user-input markers/post-marker)
    (let [user-input-after-split (content/split-user-name-from-content user-input markers/post-marker)
          user-posts (posting/post @all-users-posts user-input-after-split timestamp)]
      (reset! all-users-posts user-posts))
    nil))

(defn- when-following [all-users-posts user-input]
  (if (first-marker-in-text? user-input markers/follow-marker)
    (let [user-input-after-split (content/split-user-name-from-content user-input markers/follow-marker)
          user-posts (following/follows @all-users-posts user-input-after-split)]
      (reset! all-users-posts user-posts))
    nil))

(defn- when-wall [all-users-posts user-input timestamp]
  (if (first-marker-in-text? user-input markers/wall-marker)
    (let [user-name (:user-name (content/split-user-name-from-content user-input markers/wall-marker))
          user-posts-with-follewed-ones (wall/show-wall @all-users-posts user-name)]
      (if (seq user-posts-with-follewed-ones)
        (print (formatter/formatted-posts
                 user-posts-with-follewed-ones time/time-diffrence timestamp))))
    nil))

(defn- when-reading [all-users-posts user-input timestamp]
  (let [user-posts (reading/read-user-posts @all-users-posts user-input)]
    (if (seq user-posts)
      (print (formatter/formatted-posts
               (:posts-values user-posts) time/time-diffrence timestamp)))))

(defn usecase-execution [all-users-posts user-input timestamp]
  (or (when-posting all-users-posts user-input timestamp)
      (when-following all-users-posts user-input)
      (when-wall all-users-posts user-input timestamp)
      (when-reading all-users-posts user-input timestamp)))
