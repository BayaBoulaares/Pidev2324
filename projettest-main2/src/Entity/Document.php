<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\DocumentRepository;
use Symfony\Component\Validator\Constraints\Date;

#[ORM\Entity(repositoryClass: DocumentRepository::class)]
class Document
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private  ?int $idDoc;
    #[ORM\Column(length: 255)]
    private  ?string $titre;

    #[ORM\Column(length: 255)]
    private ?string $type;

    #[ORM\Column(length: 1000)]
    private ?string $url;

    #[ORM\Column(length:255)]
    private ?string $niveau;

    #[ORM\Column]
    private ?Date $date;

    #[ORM\ManyToOne(targetEntity: Matiere::class, inversedBy: 'documents')]
    private ?Matiere $idMat;
    

    public function getIdDoc(): ?int
    {
        return $this->idDoc;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): static
    {
        $this->titre = $titre;

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

    public function getUrl(): ?string
    {
        return $this->url;
    }

    public function setUrl(string $url): static
    {
        $this->url = $url;

        return $this;
    }

    public function getNiveau(): ?string
    {
        return $this->niveau;
    }

    public function setNiveau(string $niveau): static
    {
        $this->niveau = $niveau;

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

    public function getIdMat(): ?Matiere
    {
        return $this->idMat;
    }

    public function setIdMat(?Matiere $idMat): static
    {
        $this->idMat = $idMat;

        return $this;
    }
    public function __toString()
    {
        return $this->getIdDoc();
    }



}