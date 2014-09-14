(ns clj-chirp.content-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.content :refer :all]))

(describe "User post a message."
          (describe "User text is empty."
                    (it "Returns empty value."
                        (should= {:user "" :content ""}
                                 (split-user-from-content "->" "->"))))
          (describe "User text contains only user name."
                    (it "Returns a user with empty post"
                        (should= {:user "user bob" :content ""}
                                 (split-user-from-content "user bob ->" "->"))))
          (describe "Expected data."
                    (it "Returns a user with a post."
                        (should= {:user "user bob" :content "post"}
                                 (split-user-from-content "user bob -> post" "->"))))
          (describe "There are more tokens inside text."
                    (it "Returns a user with a post."
                        (should= {:user "user bob" :content "post ->"}
                                 (split-user-from-content "user bob -> post ->" "->")))))