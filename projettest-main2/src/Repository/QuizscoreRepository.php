<?php

namespace App\Repository;

use App\Entity\Quizscore;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Quizscore>
 *
 * @method Quizscore|null find($id, $lockMode = null, $lockVersion = null)
 * @method Quizscore|null findOneBy(array $criteria, array $orderBy = null)
 * @method Quizscore[]    findAll()
 * @method Quizscore[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class QuizscoreRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Quizscore::class);
    }

//    /**
//     * @return Quizscore[] Returns an array of Quizscore objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('q')
//            ->andWhere('q.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('q.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Quizscore
//    {
//        return $this->createQueryBuilder('q')
//            ->andWhere('q.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
