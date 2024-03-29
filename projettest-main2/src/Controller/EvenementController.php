<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Form\EvenementType;
use App\Repository\EventRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Validator\ValidatorInterface;

#[Route('/evenement')]
class EvenementController extends AbstractController
{
    #[Route('/', name: 'app_evenement_index', methods: ['GET'])]
    public function index(EventRepository $eventRepository): Response
    {
        return $this->render('evenement/index.html.twig', [
            'evenements' => $eventRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_evenement_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, ValidatorInterface $validator): Response
    {
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Handle file upload
            $imageFile = $form->get('image')->getData();
            if ($imageFile) {
                // Define constraints for the uploaded file
                $constraints = new Assert\Image([
                    'maxSize' => '5M',
                    'mimeTypes' => [
                        'image/jpeg',
                        'image/png',
                        'image/gif',
                    ],
                ]);

                // Validate the uploaded file
                $errors = $validator->validate($imageFile, $constraints);

                // Check if there are any errors
                if (count($errors) > 0) {
                    // Handle validation errors...
                    $errorMessages = [];
                    foreach ($errors as $error) {
                        $errorMessages[] = $error->getMessage();
                    }

                    // For example, you can add a flash message with the errors
                    $this->addFlash('error', implode(', ', $errorMessages));

                    // And redirect back to the form
                    return $this->redirectToRoute('app_evenement_new');
                }

                // If there are no errors, handle the file upload...
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                // Sanitize filename to avoid security issues
                $safeFilename = $originalFilename;
                // Customize filename to avoid overwriting existing files
                $newFilename = $safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();
                // Move the file to the desired directory
                $imageFile->move(
                    $this->getParameter('images_directory'),
                    $newFilename
                );
                // Store the file path in the entity
                $evenement->setImage($newFilename);
            }

            $entityManager->persist($evenement);
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/{idEvent}', name: 'app_evenement_show', methods: ['GET'])]
    public function show(Evenement $evenement): Response
    {
        return $this->render('evenement/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }

    #[Route('/{idEvent}/edit', name: 'app_evenement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/{idEvent}', name: 'app_evenement_delete', methods: ['POST'])]
    public function delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$evenement->getIdEvent(), $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/upload-image', name: 'upload_image_route', methods: ['POST'])]
public function uploadImage(Request $request, ValidatorInterface $validator): Response
{
    // Handle image upload logic here
    $uploadedFile = $request->files->get('image');

    // Check if an image file was uploaded
    if (!$uploadedFile) {
        // Return an error response indicating no file was uploaded
        return new Response('No file uploaded', Response::HTTP_BAD_REQUEST);
    }

    // Define constraints for the uploaded file
    $constraints = new Assert\Image([
        'maxSize' => '5M',
        'mimeTypes' => [
            'image/jpeg',
            'image/png',
            'image/gif',
        ],
    ]);

    // Validate the uploaded file
    $errors = $validator->validate($uploadedFile, $constraints);

    // Check if there are any validation errors
    if (count($errors) > 0) {
        // Prepare error messages
        $errorMessages = [];
        foreach ($errors as $error) {
            $errorMessages[] = $error->getMessage();
        }
        
        // Return an error response with validation error messages
    }

    // Handle the valid uploaded image
    // For example, you can move the file to a permanent location
    $newFilename = uniqid().'.'.$uploadedFile->guessExtension();
    $uploadedFile->move(
        $this->getParameter('images_directory'),
        $newFilename
    );

    // Return a success response with the path or URL of the uploaded image
}

}
