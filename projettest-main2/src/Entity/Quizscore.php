<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use App\Repository\QuizscoreRepository;

#[ORM\Entity(repositoryClass: QuizscoreRepository::class)]
class Quizscore
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idscore = null;

    #[ORM\ManyToOne(targetEntity:Quiz::class , inversedBy:' Quizscore')]
    private ?Quiz $idquiz;

    #[ORM\Column]
    private ?float $score ;

    #[ORM\ManyToOne(targetEntity:Utilisateurs::class , inversedBy:' Quizscore')]
    private ?Utilisateurs $iduser;

    public function getIdscore(): ?int
    {
        return $this->idscore;
    }

    public function getIdquiz(): ?int
    {
        return $this->idquiz;
    }

    public function setIdquiz(int $idquiz): static
    {
        $this->idquiz = $idquiz;

        return $this;
    }

    public function getScore(): ?float
    {
        return $this->score;
    }

    public function setScore(float $score): static
    {
        $this->score = $score;

        return $this;
    }

   /* public function getIduser(): ?Utilisateurs
    {
        return $this->iduser;
    }*/

    public function setIduser(?Utilisateurs $iduser): static
    {
        $this->iduser = $iduser;

        return $this;
    }


}