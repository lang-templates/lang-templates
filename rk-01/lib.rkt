#lang racket
(require compatibility/defmacro) ;; compatibility-lib

(define $counter 0)

(define (misc-counter)
  (set! $counter (+ $counter 1))
  $counter
  )

(define (misc-elt lst i)
  (cond
    ((list? lst) (list-ref lst i))
    ((vector? lst) (vector-ref lst i))
    (#t #f)
    )
  )

(define (misc-length lst)
  (cond
    ((list? lst) (length lst))
    ((vector? lst) (vector-length lst))
    (#t 0)
    )
  )

(define (misc-random-element lst)
  (misc-elt lst (random (misc-length lst))))

(define word-char-list #("a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p" "q" "r" "s" "t" "u" "v" "w" "x" "y" "z"))

#;(define (misc-random-word-old len [lst word-char-list])
  (define result "")
  (for ([_ len])
    (set! result (string-append result (misc-random-element lst)))
    )
  result
  )

(define (misc-random-word len [lst word-char-list])
  (with-output-to-string
    (λ ()
      (for ([_ len])
        (display (misc-random-element lst))
        )
      )
    )
  )

(provide
 misc-counter
 misc-length
 misc-elt
 misc-random-element
 misc-random-word
 )
