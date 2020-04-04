import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Diversity{

    final static int SIZE = 1000000;
    final static int MAX = 100;

    public static void main(String args[]){
        Individual.MAX = MAX;
        int students[] = new int[SIZE];
        for(int i=0; i<SIZE; i++){
            students[i] = (int)(Math.random()*MAX);
        }
        System.out.println();

        Individual population[] = new Individual[100];
        for(int i=0; i<100; i++){
            int chrom[] = new int[3];
            for(int j=0; j<3; j++){
                chrom[j] = students[(int)(Math.random()*(SIZE-1))];
            }
            population[i] = new Individual(chrom, students);
        }

        int generation = 0;

        while(true){
            Individual new_gen[] = new Individual[100];
            Individual temp_pol[] = new Individual[10];
            for(int i=0; i<100; i++){
                for(int j=0; j<10; j++){
                    temp_pol[j] = population[(int)(Math.random()*(100-1))];
                }

                Arrays.sort(temp_pol, new IndividualComparator());

                Individual offspring = temp_pol[0].mateIndividual(temp_pol[9], students);
                new_gen[i] = offspring;
            }

            Arrays.sort(population, new IndividualComparator());
            Arrays.sort(new_gen, new IndividualComparator());
            if(population[0].fitness <= new_gen[0].fitness) break;
            else population = new_gen;

            System.out.print("Generation "+generation+":  "+population[0].chromosome[0]+" ");
            System.out.print(population[0].chromosome[1]+" "+population[0].chromosome[2]+" ");
            System.out.print("Fitness: "+population[0].fitness+"\n");

            generation++;
        }

        System.out.print("Generation "+generation+":  "+population[0].chromosome[0]+" ");
        System.out.print(population[0].chromosome[1]+" "+population[0].chromosome[2]+" ");
        System.out.print("Fitness: "+population[0].fitness+"\n");

        List<Integer> grp1 = new ArrayList<Integer>();
        List<Integer> grp2 = new ArrayList<Integer>();
        List<Integer> grp3 = new ArrayList<Integer>();


        Arrays.sort(population[0].chromosome);

        for(int i=0; i<students.length; i++){
            if(students[i] <= (population[0].chromosome[0]+population[0].chromosome[1])/2){
                grp1.add(students[i]);
            }
            else if(students[i] <= (population[0].chromosome[1]+population[0].chromosome[2])/2){
                grp2.add(students[i]);
            }
            else{
                grp3.add(students[i]);
            }
        }

    }
}

class Individual{
    public int chromosome[];
    public double fitness;
    public static int MAX;

    public Individual(int chromosome[], int students[]){
        this.chromosome = new int[3];
        for(int i=0; i<3; i++) this.chromosome[i] = chromosome[i];
        this.fitness = calc_fitness(students);
    }

    public double calc_fitness(int students[]){
        double fit = 0;
        double max[] = {0.0, 0.0, 0.0};

        List<Integer> grp1 = new ArrayList<Integer>();
        List<Integer> grp2 = new ArrayList<Integer>();
        List<Integer> grp3 = new ArrayList<Integer>();


        Arrays.sort(this.chromosome);

        for(int i=0; i<students.length; i++){
            if(students[i] <= (chromosome[0]+chromosome[1])/2){
                grp1.add(students[i]);
            }
            else if(students[i] <= (chromosome[1]+chromosome[2])/2){
                grp2.add(students[i]);
            }
            else{
                grp3.add(students[i]);
            }
        }
        
        if(grp1.size() == 0) max[0] = MAX;
        else {
            double temp = 0;
            for(int i=0; i<grp1.size(); i++) temp+=Math.abs(grp1.get(i)-chromosome[0]);
            temp /= grp1.size();
            max[0] = temp;
        }

        if(grp2.size() == 0) max[1] = MAX;
        else {
            double temp = 0;
            for(int i=0; i<grp2.size(); i++) temp+=Math.abs(grp2.get(i)-chromosome[1]);
            temp /= grp2.size();
            max[1] = temp;
        }

        if(grp3.size() == 0) max[2] = MAX;
        else {
            double temp = 0;
            for(int i=0; i<grp3.size(); i++) temp+=Math.abs(grp3.get(i)-chromosome[2]);
            temp /= grp3.size();
            max[2] = temp;
        }

        for(int i=0; i<3; i++) fit += max[i];

        return fit;
    }

    public Individual mateIndividual(Individual par, int students[]){
        int chrom[] = new int[3];

        for(int i=0; i<3; i++){
            double prob = Math.random();
            if(prob <= 0.10){
                chrom[i] = (int)(Math.random()*MAX);
            }
            else{
                if(i == 0) chrom[i] = (this.chromosome[i] < par.chromosome[i])? this.chromosome[i]: par.chromosome[i];
                else if(i == 2) chrom[i] = (this.chromosome[i] > par.chromosome[i])? this.chromosome[i]: par.chromosome[i];
                else chrom[i] = (this.chromosome[i] + par.chromosome[i])/2;
            }
        }

        return new Individual(chrom, students);
    } 

}

class IndividualComparator implements Comparator<Individual>{

    public int compare(Individual i1, Individual i2){
        double temp = i1.fitness - i2.fitness;
        if(temp < 0) return -1;
        else if(temp > 0) return 1;
        else return 0;
    }

}