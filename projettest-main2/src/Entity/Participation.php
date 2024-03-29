<?php

namespace App\Entity;
use App\Repository\ParticipationRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ParticipationRepository::class)]

class Participation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idParticipation;

    //#[ORM\ManyToMany(inversed: 'Evenement')]
    private ?Evenement $idEvent;

    
  //  #[ORM\ManyToMany(inversed: 'Utilisateurs')]
    private ?Utilisateurs $idUser;

    public function getIdParticipation(): ?int
    {
        return $this->idParticipation;
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

    public function getIdUser(): ?Utilisateurs
    {
        return $this->idUser;
    }

    public function setIdUser(?Utilisateurs $idUser): static
    {
        $this->idUser = $idUser;

        return $this;
    }


}
