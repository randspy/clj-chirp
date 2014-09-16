(ns clj-chirp.content-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.content :refer :all]))

(describe "User post a message."
          (describe "User text is empty."
                    (it "Returns empty value."
                        (should= {:user-name "" :content ""}
                                 (split-user-name-from-content "->" "->"))))
          (describe "User text contains only user name."
                    (it "Returns a user with empty post"
                        (should= {:user-name "user" :content ""}
                                 (split-user-name-from-content "user ->" "->"))))
          (describe "Expected data."
                    (it "Returns a user with a post."
                        (should= {:user-name "user" :content "post"}
                                 (split-user-name-from-content "user -> post" "->"))))
          (describe "There are more tokens inside text."
                    (it "Returns a user with a post."
                        (should= {:user-name "user" :content "post ->"}
                                 (split-user-name-from-content "user -> post ->" "->")))))