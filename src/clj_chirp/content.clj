(ns clj-chirp.content)


(defn- trim [string]
  (.trim (if (empty? string) "" string)))

(defn split-user-from-content [user-input split-token]
  (let [number-of-split-elements 2
        tokens (clojure.string/split
                 user-input (re-pattern split-token) number-of-split-elements)
        user-name (first tokens)
        post (clojure.string/join (second tokens))]
    {:user (trim user-name) :content (trim post)}))