<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HomeBackController extends AbstractController
{
    #[Route('/homeback', name: 'app_home_back')]
    public function index(): Response
    {
        return $this->render('home/homeBack.html.twig', [
            'controller_name' => 'HomeBackController',
        ]);
    }
}
