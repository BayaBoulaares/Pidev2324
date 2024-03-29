<?php

namespace App\Form;

use App\Entity\Evenement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType; // Add this line
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomEvent')
            ->add('dateDebut', DateType::class, [
                'label' => 'Date Début',
                'widget' => 'single_text',
            ])
            ->add('dateFin', DateType::class, [
                'label' => 'Date Fin',
                'widget' => 'single_text',
            ])
            ->add('lieuEvent')
            ->add('description')
            ->add('nbMax')
            ->add('image', FileType::class, [
                'label' => 'Image (JPG, PNG, or GIF file)',
                'mapped' => false, 
                'required' => false, 
                'attr' => ['accept' => 'image/*'] 
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Evenement::class,
        ]);
    }
}
