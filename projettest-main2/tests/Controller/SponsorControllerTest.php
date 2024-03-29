<?php

namespace App\Test\Controller;

use App\Entity\Sponsor;
use App\Repository\SponsorRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\KernelBrowser;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class SponsorControllerTest extends WebTestCase
{
    private KernelBrowser $client;
    private SponsorRepository $repository;
    private string $path = '/sponsor/';
    private EntityManagerInterface $manager;

    protected function setUp(): void
    {
        $this->client = static::createClient();
        $this->repository = static::getContainer()->get('doctrine')->getRepository(Sponsor::class);

        foreach ($this->repository->findAll() as $object) {
            $this->manager->remove($object);
        }
    }

    public function testIndex(): void
    {
        $crawler = $this->client->request('GET', $this->path);

        self::assertResponseStatusCodeSame(200);
        self::assertPageTitleContains('Sponsor index');

        // Use the $crawler to perform additional assertions e.g.
        // self::assertSame('Some text on the page', $crawler->filter('.p')->first());
    }

    public function testNew(): void
    {
        $originalNumObjectsInRepository = count($this->repository->findAll());

        $this->markTestIncomplete();
        $this->client->request('GET', sprintf('%snew', $this->path));

        self::assertResponseStatusCodeSame(200);

        $this->client->submitForm('Save', [
            'sponsor[nom]' => 'Testing',
            'sponsor[description]' => 'Testing',
            'sponsor[fond]' => 'Testing',
            'sponsor[image]' => 'Testing',
            'sponsor[idEvent]' => 'Testing',
        ]);

        self::assertResponseRedirects('/sponsor/');

        self::assertSame($originalNumObjectsInRepository + 1, count($this->repository->findAll()));
    }

    public function testShow(): void
    {
        $this->markTestIncomplete();
        $fixture = new Sponsor();
        $fixture->setNom('My Title');
        $fixture->setDescription('My Title');
        $fixture->setFond('My Title');
        $fixture->setImage('My Title');
        $fixture->setIdEvent('My Title');

        $this->manager->persist($fixture);
        $this->manager->flush();

        $this->client->request('GET', sprintf('%s%s', $this->path, $fixture->getId()));

        self::assertResponseStatusCodeSame(200);
        self::assertPageTitleContains('Sponsor');

        // Use assertions to check that the properties are properly displayed.
    }

    public function testEdit(): void
    {
        $this->markTestIncomplete();
        $fixture = new Sponsor();
        $fixture->setNom('My Title');
        $fixture->setDescription('My Title');
        $fixture->setFond('My Title');
        $fixture->setImage('My Title');
        $fixture->setIdEvent('My Title');

        $this->manager->persist($fixture);
        $this->manager->flush();

        $this->client->request('GET', sprintf('%s%s/edit', $this->path, $fixture->getId()));

        $this->client->submitForm('Update', [
            'sponsor[nom]' => 'Something New',
            'sponsor[description]' => 'Something New',
            'sponsor[fond]' => 'Something New',
            'sponsor[image]' => 'Something New',
            'sponsor[idEvent]' => 'Something New',
        ]);

        self::assertResponseRedirects('/sponsor/');

        $fixture = $this->repository->findAll();

        self::assertSame('Something New', $fixture[0]->getNom());
        self::assertSame('Something New', $fixture[0]->getDescription());
        self::assertSame('Something New', $fixture[0]->getFond());
        self::assertSame('Something New', $fixture[0]->getImage());
        self::assertSame('Something New', $fixture[0]->getIdEvent());
    }

    public function testRemove(): void
    {
        $this->markTestIncomplete();

        $originalNumObjectsInRepository = count($this->repository->findAll());

        $fixture = new Sponsor();
        $fixture->setNom('My Title');
        $fixture->setDescription('My Title');
        $fixture->setFond('My Title');
        $fixture->setImage('My Title');
        $fixture->setIdEvent('My Title');

        $this->manager->persist($fixture);
        $this->manager->flush();

        self::assertSame($originalNumObjectsInRepository + 1, count($this->repository->findAll()));

        $this->client->request('GET', sprintf('%s%s', $this->path, $fixture->getId()));
        $this->client->submitForm('Delete');

        self::assertSame($originalNumObjectsInRepository, count($this->repository->findAll()));
        self::assertResponseRedirects('/sponsor/');
    }
}
