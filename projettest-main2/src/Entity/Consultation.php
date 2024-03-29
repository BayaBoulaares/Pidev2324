<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\ConsultationRepository;

#[ORM\Entity(repositoryClass: ConsultationRepository::class)]
class Consultation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private  ?int $id;

    #[ORM\ManyToMany(targetEntity: Matiere::class,inversedBy:'consultation1')]
    private  ?Matiere $idmat;

    #[ORM\ManyToMany(targetEntity:Utilisateurs::class,inversedBy:'consultation2')]
    private  ?Utilisateurs $idut;

    public function __construct()
    {
        $this->idmat = new ArrayCollection();
        $this->idut = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdmat(): ?Matiere
    {
        return $this->idmat;
    }

    public function setIdmat(?Matiere $idmat): static
    {
        $this->idmat = $idmat;

        return $this;
    }

    public function getIdut(): ?Utilisateurs
    {
        return $this->idut;
    }

    public function setIdut(?Utilisateurs $idut): static
    {
        $this->idut = $idut;

        return $this;
    }
    public function __toString()
    {
        return $this->getId();
    }

    /*public function addIdmat(Matiere $idmat): static
    {
        if (!$this->idmat->contains($idmat)) {
            $this->idmat->add($idmat);
        }

        return $this;
    }

    public function removeIdmat(Matiere $idmat): static
    {
        $this->idmat->removeElement($idmat);

        return $this;
    }

    public function addIdut(Utilisateurs $idut): static
    {
        if (!$this->idut->contains($idut)) {
            $this->idut->add($idut);
        }

        return $this;
    }

    public function removeIdut(Utilisateurs $idut): static
    {
        $this->idut->removeElement($idut);

        return $this;
    }*/

}