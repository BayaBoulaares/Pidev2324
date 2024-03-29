<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use DateTime;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;

#[ORM\Entity(repositoryClass: ReclamationRepository::class)]

class Reclamation
{
    
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null ;
  
    #[ORM\Column(length: 255)]
    private ?string $nom;
    

    #[ORM\Column(length: 255)]
    private ?string $reclamation;

   
    #[ORM\Column]
    private ?Date $date;
   

    #[ORM\Column(length: 255)]
    private ?string $rating;

    
    #[ORM\Column(length: 255)]    
    private ?string $type;

    #[ORM\Column]
    private ?int $traitement;

   
    #[ORM\ManyToOne(inversedBy: 'reclamations')]
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

    public function getReclamation(): ?string
    {
        return $this->reclamation;
    }

    public function setReclamation(string $reclamation): static
    {
        $this->reclamation = $reclamation;

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

    public function getRating(): ?string
    {
        return $this->rating;
    }

    public function setRating(string $rating): static
    {
        $this->rating = $rating;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getTraitement(): ?int
    {
        return $this->traitement;
    }

    public function setTraitement(int $traitement): static
    {
        $this->traitement = $traitement;

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


}