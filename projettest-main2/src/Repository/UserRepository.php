<?php
namespace App\Repository;

use App\Entity\Utilisateurs;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class UserRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Utilisateurs::class);
    }
   public function findOneBySomeField(): array
   {
       return $this->createQueryBuilder('a')
           ->andWhere('a.role = :role')
           ->setParameter('role', 'Professeur')
           ->getQuery()
           ->getResult();
       ;
   }
    

}
?>