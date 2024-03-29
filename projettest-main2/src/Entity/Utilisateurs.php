<?php

namespace App\Entity;
use App\Repository\UserRepository;

use DateTime;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use PhpParser\Node\Expr\Cast\String_;

#[ORM\Entity(repositoryClass: UserRepository::class)]

class Utilisateurs
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idu = null;

    #[ORM\Column(length: 255)]
    private ?String $nom;

    #[ORM\Column(length: 255)]
    private ?String $prenom = null;

    #[ORM\Column(length: 255)]
    private ?String $adresse = null;

    #[ORM\Column]
    private ?DateTime $dob = null;

    #[ORM\Column(length: 255)]
    private ?String $login = null;

    #[ORM\Column]
    private ?int $tel = null;

    #[ORM\Column(length: 255)]
    private ?String $role = null;

    #[ORM\Column(length: 255)]
    private ?String $mdp = null;

    #[ORM\Column(length: 255)]
    private ?String $discipline = null;

    #[ORM\Column(length: 255)]
    private ?String $nomenfant= null;

    #[ORM\Column(length: 255)]
    private ?String $prenomenfant = null;

    #[ORM\Column]
    private ?String $dobenfant = null;

    #[ORM\Column]
    private ?int$niveau = null;

    #[ORM\Column]
    private ?String $image = null;

    public function getIdu(): ?int
    {
        return $this->idu;
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

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): static
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): static
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getDob(): ?\DateTimeInterface
    {
        return $this->dob;
    }

    public function setDob(\DateTimeInterface $dob): static
    {
        $this->dob = $dob;

        return $this;
    }

    public function getLogin(): ?string
    {
        return $this->login;
    }

    public function setLogin(string $login): static
    {
        $this->login = $login;

        return $this;
    }

    public function getTel(): ?int
    {
        return $this->tel;
    }

    public function setTel(int $tel): static
    {
        $this->tel = $tel;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): static
    {
        $this->role = $role;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): static
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getDiscipline(): ?string
    {
        return $this->discipline;
    }

    public function setDiscipline(?string $discipline): static
    {
        $this->discipline = $discipline;

        return $this;
    }

    public function getNomenfant(): ?string
    {
        return $this->nomenfant;
    }

    public function setNomenfant(string $nomenfant): static
    {
        $this->nomenfant = $nomenfant;

        return $this;
    }

    public function getPrenomenfant(): ?string
    {
        return $this->prenomenfant;
    }

    public function setPrenomenfant(string $prenomenfant): static
    {
        $this->prenomenfant = $prenomenfant;

        return $this;
    }

    public function getDobenfant(): ?\DateTimeInterface
    {
        return $this->dobenfant;
    }

    public function setDobenfant(?\DateTimeInterface $dobenfant): static
    {
        $this->dobenfant = $dobenfant;

        return $this;
    }

    public function getNiveau(): ?int
    {
        return $this->niveau;
    }

    public function setNiveau(int $niveau): static
    {
        $this->niveau = $niveau;

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


}
