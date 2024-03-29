<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\SponsorRepository;


#[ORM\Entity(repositoryClass: SponsorRepository::class)]

class Sponsor
{
   #[ORM\Id]
   #[ORM\GeneratedValue]
   #[ORM\Column]
    private ?int $idSopnsor;

    #[ORM\Column(length: 255)]
    private ?String  $nom;

    #[ORM\Column(length: 255)]
    private ?String  $description;
    #[ORM\Column]
    private ?String $fond;

    #[ORM\Column]
    private ?String $image;
    
   // #[ORM\OneToMany(inversed: 'Evenement')]
    #[ORM\Column]
    private ?Evenement $idEvent = null;


    public function getIdSopnsor(): ?int
    {
        return $this->idSopnsor;
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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getFond(): ?string
    {
        return $this->fond;
    }

    public function setFond(string $fond): static
    {
        $this->fond = $fond;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image): static
    {
        $this->image = $image;

        return $this;
    }

    public function getIdEvent(): ?Evenement
    {
        return $this->idEvent;
    }

    public function setIdEvent(?Evenement $idEvent): static
    {
        $this->idEvent = $idEvent;

        return $this;
    }


}
