#! /usr/bin/env racket
#lang racket
(require "./main.rkt")
(require access)
(require output)

(misc-counter)
(misc-counter)
(misc-counter)

(misc-random-element '(apple carrot tomato cucumber))
(misc-random-element '(apple carrot tomato cucumber))
(misc-random-element '(apple carrot tomato cucumber))

(misc-random-word 10)
(misc-random-word 10 #("x" "y" "z"))
(misc-random-word 10 #("x" "y" "z"))
(misc-random-word 10 #("x" "y" "z"))

(dump (between 1.0 1.2))
(dump (between 10 2))

(sleep-between 0.1 0.2)
(sleep-between 0.1 0.2 1000)
