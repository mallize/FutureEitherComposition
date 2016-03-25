package net.clementmatthew.util

import scala.concurrent.{ExecutionContext, Future}

object FutureEitherUtil {
   def applyEither[L, R1, R2](f: (R1) => Future[Either[L, R1]], e: Either[L, R1])(implicit ec: ExecutionContext): Future[Either[L, R1]] = {
      e match {
         case Left(l) => Future.successful(Left(l))
         case Right(r) => f(r)
      }
   }

   def applyEither1[L, R1, R2, A](f: (R1, A) => Future[Either[L, R1]], e: Either[L, R1], s: A)(implicit ec: ExecutionContext): Future[Either[L, R1]] = {
      e match {
         case Left(l) => Future.successful(Left(l))
         case Right(r) => f(r, s)
      }
   }

   def applyEither2[L, R1, R2, A, B](f: (R1, A, B) => Future[Either[L, R2]], e: Either[L, R1], s: A, s2: B)(implicit ec: ExecutionContext): Future[Either[L, R2]] = {
      e match {
         case Left(l) => Future.successful(Left(l))
         case Right(r) => f(r, s, s2)
      }
   }

   def applyEither3[L, R1, R2, A, B, C](f: (R1, A, B, C) => Future[Either[L, R2]], e: Either[L, R1], s: A, s2: B, s3: C)(implicit ec: ExecutionContext): Future[Either[L, R2]] = {
      e match {
         case Left(l) => Future.successful(Left(l))
         case Right(r) => f(r, s, s2, s3)
      }
   }

   def applyEither4[L, R1, R2, A, B, C, D](f: (R1, A, B, C, D) => Future[Either[L, R2]], e: Either[L, R1], s: A, s2: B, s3: C, s4: D)(implicit ec: ExecutionContext): Future[Either[L, R2]] = {
      e match {
         case Left(l) => Future.successful(Left(l))
         case Right(r) => f(r, s, s2, s3, s4)
      }
   }

}
