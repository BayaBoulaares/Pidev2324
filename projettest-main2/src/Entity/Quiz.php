<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use App\Repository\QuizRepository;

#[ORM\Entity(repositoryClass: QuizRepository::class)]

class Quiz
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idqz = null;


    #[ORM\Column(length: 255)]
    private $type;

    #[ORM\Column(length: 255)]
    private $name;

    #[ORM\Column(length: 255)]
    private $image;

    public function getIdqz(): ?int
    {
        return $this->idqz;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(?string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): static
    {
        $this->name = $name;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): static
    {
        $this->image = $image;

        return $this;
    }


}