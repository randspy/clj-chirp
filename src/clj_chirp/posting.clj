(ns clj-chirp.posting)

(defn trim [string]
  (.trim (if (empty? string) "" string)))

(defn post [user-input split-token]
  (let [number-of-split-elements 2
        tokens (clojure.string/split
                 user-input (re-pattern split-token) number-of-split-elements)
        user-name (first tokens)
        post (clojure.string/join (second tokens))]
    {:user (trim user-name) :post (trim post)}))
