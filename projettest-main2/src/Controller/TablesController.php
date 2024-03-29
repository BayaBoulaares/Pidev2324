<?php

namespace App\Controller;

use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;

class TablesController extends AbstractController
{
    #[Route('/tables', name: 'app_tables')]
    public function index(): Response
    {
        return $this->render('tables/tables.html.twig', [
            'controller_name' => 'TablesController',
        ]);
    }
    #[Route('/tablesSH', name: 'tables')]
    public function tables(UserRepository $repo): Response
    {  
        return $this->render('tables/tables.html.twig', [
            'controller_name' => 'TablesController',
        ]);
    }

}
