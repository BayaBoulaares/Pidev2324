<?php

namespace App\Controller;

use App\Entity\Utilisateurs;
use App\Form\FormProfType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Routing\Annotation\Route;

class FormBackController extends AbstractController
{
    #[Route('/form/back', name: 'app_form_back')]
    public function index(): Response
    {
        return $this->render('form_back/formProf.html.twig', [
            'controller_name' => 'FormBackController',
        ]);
    }
    
 
   
    
   



}
