#lang racket

(define-syntax-rule (&swap x y)
  (let ([tmp x])
    (set! x y)
    (set! y tmp)))

(define (between n1 n2 [resolution 100])
  (when (> n1 n2) (&swap n1 n2))
  #;(dump n1)
  #;(dump n2)
  (define r (random 0 resolution))
  (define rf (/ r (* resolution 1.0)))
  (define diff (- n2 n1))
  #;(dump diff)
  #;(dump r)
  #;(dump rf)
  (define result (+ n1 (* diff rf)))
  #;(dump result)
  (cond
    ((< result n1) n1)
    ((> result n2) n2)
    (else result)
    )
  )

(define (sleep-between n1 n2 [resolution 100])
  (define n (between n1 n2 resolution))
  (sleep n)
  )

(provide
 between
 sleep-between
 )
