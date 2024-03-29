<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\MatiereRepository;

#[ORM\Entity(repositoryClass: MatiereRepository::class)]

class Matiere
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id=null;

    #[ORM\Column(length: 255)]
    private  ?string $nomMatiere;
    #[ORM\Column(length: 255)]
    private  ?string $description;

    #[ORM\Column(length: 255)]
    private ?string $annee;

    #[ORM\Column(length: 255)]
    private ?string  $categorie;

    #[ORM\ManyToOne(targetEntity:Utilisateurs::class , inversedBy:' matieres')]
    private ?Utilisateurs $idu;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomMatiere(): ?string
    {
        return $this->nomMatiere;
    }

    public function setNomMatiere(string $nomMatiere): static
    {
        $this->nomMatiere = $nomMatiere;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getAnnee(): ?string
    {
        return $this->annee;
    }

    public function setAnnee(string $annee): static
    {
        $this->annee = $annee;

        return $this;
    }

    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    public function setCategorie(string $categorie): static
    {
        $this->categorie = $categorie;

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