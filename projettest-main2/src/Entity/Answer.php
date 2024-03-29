<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

use App\Repository\AnswerRepository;

#[ORM\Entity(repositoryClass: AnswerRepository::class)]
class Answer
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private $answerText;

    #[ORM\Column]
    private $isTrue;

    #[ORM\Column]
    //private ?Date $answerDate ;
   
    
    #[ORM\ManyToOne(targetEntity:Utilisateurs::class , inversedBy:' Answer')]
    private ?Utilisateurs $iduser;


    #[ORM\ManyToOne(targetEntity:Question::class , inversedBy:' Answer')]
    private ?Question $question;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAnswerText(): ?string
    {
        return $this->answerText;
    }

    public function setAnswerText(?string $answerText): static
    {
        $this->answerText = $answerText;

        return $this;
    }

    public function isIsTrue(): ?bool
    {
        return $this->isTrue;
    }

    public function setIsTrue(?bool $isTrue): static
    {
        $this->isTrue = $isTrue;

        return $this;
    }

    /*public function getAnswerDate(): ?\DateTimeInterface
    {
        return $this->answerDate;
    }

    public function setAnswerDate(\DateTimeInterface $answerDate): static
    {
        $this->answerDate = $answerDate;

        return $this;
    }*/

    /*public function getIduser(): ?Utilisateurs
    {
        return $this->iduser;
    }*/

    public function setIduser(?Utilisateurs $iduser): static
    {
        $this->iduser = $iduser;

        return $this;
    }

   /* public function getQuestion(): ?Question
    {
        return $this->question;
    }*/

    public function setQuestion(?Question $question): static
    {
        $this->question = $question;

        return $this;
    }


}