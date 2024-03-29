<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


use App\Repository\QuestionRepository;

#[ORM\Entity(repositoryClass: QuestionRepository::class)]
class Question
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idq = null;

    #[ORM\Column(length: 255)]
    private $questiontext;

    #[ORM\Column(length: 255)]
    private $choix1;

    #[ORM\Column(length: 255)]
    private $choix2;

    #[ORM\Column(length: 255)]
    private $choix3;

    #[ORM\Column(length: 255)]
    private $choix4;

    #[ORM\Column(length: 255)]
    private $choixCorrecte;


    #[ORM\ManyToOne(targetEntity:Quiz::class , inversedBy:' Question')]
    private ?Quiz $idqz;

    public function getIdq(): ?int
    {
        return $this->idq;
    }

    public function getQuestiontext(): ?string
    {
        return $this->questiontext;
    }

    public function setQuestiontext(?string $questiontext): static
    {
        $this->questiontext = $questiontext;

        return $this;
    }

    public function getChoix1(): ?string
    {
        return $this->choix1;
    }

    public function setChoix1(string $choix1): static
    {
        $this->choix1 = $choix1;

        return $this;
    }

    public function getChoix2(): ?string
    {
        return $this->choix2;
    }

    public function setChoix2(string $choix2): static
    {
        $this->choix2 = $choix2;

        return $this;
    }

    public function getChoix3(): ?string
    {
        return $this->choix3;
    }

    public function setChoix3(string $choix3): static
    {
        $this->choix3 = $choix3;

        return $this;
    }

    public function getChoix4(): ?string
    {
        return $this->choix4;
    }

    public function setChoix4(string $choix4): static
    {
        $this->choix4 = $choix4;

        return $this;
    }

    public function getChoixCorrecte(): ?string
    {
        return $this->choixCorrecte;
    }

    public function setChoixCorrecte(string $choixCorrecte): static
    {
        $this->choixCorrecte = $choixCorrecte;

        return $this;
    }

   /* public function getIdqz(): ?Quiz
    {
        return $this->idqz;
    }*/

    public function setIdqz(?Quiz $idqz): static
    {
        $this->idqz = $idqz;

        return $this;
    }


}