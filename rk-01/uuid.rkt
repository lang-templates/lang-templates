#lang racket
(require uuid)

(define (::uuid:string)
  (uuid-string)
  )

(define (::uuid:symbol)
  (uuid-symbol)
  )

(provide
::uuid:string
::uuid:symbol
 )
