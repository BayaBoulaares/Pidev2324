<?php

namespace App\Entity;

use App\Repository\MessagerieRepository;
use DateTime;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;

#[ORM\Entity(repositoryClass: MessagerieRepository::class)]



class Messagerie
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null ;

    #[ORM\Column(length: 255)]
    private ?string $nom;

    #[ORM\Column]

    private ?Date $date;

    #[ORM\Column(length: 255)]
    private ?string $message;

    #[ORM\ManyToOne(inversedBy: 'Messageries')]
    #[ORM\Column]
    private ?Utilisateurs $idu = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): static
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): static
    {
        $this->date = $date;

        return $this;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(string $message): static
    {
        $this->message = $message;

        return $this;
    }

    public function getIdu(): ?Utilisateurs
    {
        return $this->idu;
    }

    public function setIdu(?Utilisateurs $idu): static
    {
        $this->idu = $idu;

        return $this;
    }
    public function __toString()
    {
        return $this->getId();
    }

}