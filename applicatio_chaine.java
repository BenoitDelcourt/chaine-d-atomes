import java.awt.*;
import java.util.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;
public class applicatio_chaine{
    static appli_chn_datomes applic_chn_datomes;
    public static void main(String[] args) {
	applic_chn_datomes = new appli_chn_datomes("Fenetre de choix de programmes");
	applic_chn_datomes.run();
    }
}	
class appli_chn_datomes extends Frame{
     Event e;MenuItem mi;
    ensemble_de_chaines ensemble[]=new ensemble_de_chaines[2];
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    long temps_initial_en_secondes,temps_minimum=360;
    boolean occupied=false,choix_pas_chaine=false,choix_nb_elem=false,choix_masse_chaine=false,directement=false;
    Image image;
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    private SimpleDateFormat formatter;Graphics gr; 
    double coeff[]=new double[50];double coef_classes[]=new double[50];
    int mode[]=new int[50];commentaire comm=null;
    int ppmouseh;int ppmousev;boolean relachee,pressee,cliquee,draguee;
    final double dimensionpixel=4e-12;int i_run;
    boolean cree=false;double temps;
    boolean second_ensemble_virtuel=false,second_ensemble_virtuel_init=false;
    static final double pi=3.141592652;static final int dim=100;
    int right_chaine=800,top_demarre=200,left_demarre=50,bottom_demarre=420,right_demarre=700;
    static final double qat=1.6e-19,longueur_element0=0.05,d0=3.0E-5;
    static final int yyy=30,dxxx=20,xtch=30,top0=50,left0=330,bot0=80,right0=530,top5=50,left5=630,bot5=80,right5=830,top2=50,left2=30,right2=230,bot2=80;
    static final double	diamch0=0.0,diamch1=100.0,diamsig0=0.0,diamsig1=30.0,min6=0,max6=2e-5,minmol=0.0,maxmol=100.0;
    int i_demarre_dernier=-2;long temps_en_sec=0;String d_ou_je_reviens;
    int nb_elem[]= new int[2];
    int iichoisi;double dx_pt_souris, dy_pt_souris, xrepo_dep;
    float min0,max0,min5,max5,min2,max2;
    String string_menu[]=new String [11];
    boolean demo_demarrer=false;
    double xxxx, yyyy,xee,yee;int n_ensembles;
    double t;String comment;boolean peindre,toutdebut=true;
    int nq_chaine,n_counts;boolean creation_chaines;
    private MouseStatic mm;
    int aaa;//Thread Th1;
    boolean run_applet;
    double masse_chaine0=20,pas_chaine0=1.0e-10;
    double masse_chaine[]=new double [2];
    double pas_chaine[]=new double [2];
	int isleep;
    appli_chn_datomes(String s){
	super(s);  
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    dispose();
		    //rajouter ici les fenetres qui pourraient être ouvetes, du type systt.dispose();
		    if(comm!=null){
			comm.dispose();
			comm=null;
		    }
		for (int ij=0;ij<2;ij++)
		    if (ensemble[ij]!=null){
			ensemble[ij].dispose();
			ensemble[ij]=null;
 		    }

		    System.exit(0);  // pour ne pas laisser trainer des applications qui ne sont pas actives mais prennent de la place en mémoire
		};
	    });
	toutdebut=true;
	run_applet=true;peindre=true;
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	 setBackground(Color.white);
	formatter=new SimpleDateFormat ("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
	Date maintenant=new Date();
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	System.out.println("maintenant "+maintenant+" s "+temps_initial_en_secondes);
	d_ou_je_reviens="";
	n_counts=0;
	for(int i=0;i<2;i++){
	    masse_chaine[i]=masse_chaine0;
	    pas_chaine[i]=pas_chaine0;
	}
	nq_chaine=dim/4;
	cliquee=false;relachee=false;pressee=false;draguee=false;
	string_menu[0]="Propagation de deux ondes differentes et deformation d'un signal. Defaut: 200 atomes. ";//0
	string_menu[1]=" Propagation d'une onde avec et sans terminaison.  Defaut: 30 atomes.";//1
	string_menu[2]="Somme de deux ondes identiques mais déphasées ou pas. Defaut: 200 atomes.";//2
	string_menu[3]="Masses inégales. Sonars, echographie. Defaut: 200 atomes.";//3
	string_menu[4]="Oscillations forcees et frequence de coupure. Defaut: 30 atomes.";//4
	string_menu[5]="Comparer deux ondes stationnaires, boucle ouverte.  Defaut: 30 atomes.";//5
	string_menu[6]="Comparer deux ondes stationnaires, boucle fermee.  Defaut: 30 atomes. ";//6
	string_menu[7]="Partir d'un etat initial immobile triangulaire, mode pas_a_pas.  Defaut: 30 atomes. ";//7
	string_menu[8]=" Partir d'un etat initial immobile,mode pas_a_pas. Defaut: 50 atomes.";//8
	string_menu[9]="2 atomes. Creer deux ensembles a modifier par menus.";//9
	string_menu[10]="3 atomes. Creer deux ensembles a modifier par menus.";//10
	min0=(float)0.0; max0=(float)2.0e-10; min2=0;max2=200;
	min5=(float)1.0; max5=(float)100.0;
	nb_elem[0]=(int)Math.round((float)(max2+min2)/2);
	nb_elem[1]=nb_elem[0];
	creation_chaines=true;n_ensembles=0;comment="";
	isleep=200;
	pack();setVisible(true);	
	setSize(1000,700);
	setLocation(0,0);
	gr=getGraphics();
	image=createImage(600,400);
	Graphics gTTampon=image.getGraphics();
	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/chaine_d_atomes_premiere_page.jpg";
	image=Toolkit.getDefaultToolkit().getImage(name);
	MediaTracker tracker=new MediaTracker(this);
	tracker.addImage(image,0); 
	try {tracker.waitForID(0); }
	catch (InterruptedException e) {
	    System.out.println(" image pas arrivee?");
	}
	//gTTampon.fillRect(50,50,100,100);
	//Image image=getImage(getCodeBase(),name);
	//part_d_entropie[10000]=0;
	gTTampon.dispose();

    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h=Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return (h*3600+m*60+s);
    }
    /*    
    public void start()
    {System.out.println(" start");
    Th1=new Thread(this);Th1.start();
    }
    */
    public void run(){
	int isleep=1;
	fin_de_programme:
	while (run_applet){
	    Date now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_secondes>temps_minimum){
		run_applet=true;break fin_de_programme; 
	    }
	    if(toutdebut){
		d_ou_je_reviens="";
		gr.setColor(Color.black);gr.setFont(times_gras_24);
		pas_chaine[0]=pas_chaine0;
		nb_elem[0]=60;
		nb_elem[1]=60;
		right_chaine=420;
		if(!demo_demarrer){
		    demo_demarrer=true;
		    n_ensembles=2;
		    creation_d_un_ensemble(0,-1,true);
		    ensemble[0].oscillations_forcees_sinusoidales=false;
		    ensemble[0].ajouter_osc=true;
		    ensemble[0].zoom_x=-1;
		    ensemble[0].facx=0.25;
		    creation_d_un_ensemble(1,-1,true);
		    ensemble[0].ajouter_osc=true;
		    //ensemble[1].pas_a_pas=true;
		    ensemble[1].oscillations_forcees_sinusoidales=false;
		    ensemble[1].zoom_x=-1;
		    ensemble[1].facx=0.25;
		    //ensemble[1].frequence=ensemble[0].frequence/2;
		    int imagew=image.getWidth(this);
		    int imageh=image.getHeight(this);
		    gr.drawImage(image,430,50,imagew,imageh,null);
		    cliquee=false;
		    pressee=false;
		}else{
		    if (!cliquee){
			for(int ii=0;ii<n_ensembles;ii++)
			    if(!(ensemble[ii].pressee&&!ensemble[ii].trouve_deplacement)&&!ensemble[ii].desactiver&&!occupied){
				ensemble[ii].run();
				ensemble[ii].paint_ensemble();
			    }
		    }else{
			cliquee=false;
			pressee=false;
			nb_elem[0]=60;
			nb_elem[1]=60;
			right_chaine=800;
			System.out.println(" annih  0 ");
			System.out.println(" ensemble[0] "+ensemble[0]+" ensemble[1] "+ensemble[1]);
			ensemble[0].dispose();
			ensemble[0]=null;
			ensemble[1].dispose();
			ensemble[1]=null;
			n_ensembles=0;
			toutdebut=false;
			peindre=true;
			cliquee=false;relachee=false;pressee=false;draguee=false;
			gr.setColor(Color.white);
			eraserect(gr, 0,0,1000,1100);
		    }
		}
	    }else{
		if(d_ou_je_reviens==""){
		    if(peindre){
			System.out.println("va peindre");
			setVisible(true);
			    menu_principal_ou_fin();
		    }
		}else if(d_ou_je_reviens!=""){
		    System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
		    for (int ii=0;ii<2;ii++)
			if (ensemble[ii]!=null){
			    ensemble[ii].dispose();
			    ensemble[ii]=null;
			}
		    creation_chaines=true;
		    peindre=true;
		    relachee=false;
		    pressee=false;
		    draguee=false;
		    cliquee=false;
		    setVisible(true);
		    choix_pas_chaine=false;
		    choix_nb_elem=false;
		    choix_masse_chaine=false;
		    directement=false;	
		    creation_chaines=true;
		    n_ensembles=0;
		    setVisible(true);
		    for(int i=0;i<20;i++)
			System.out.println("****va peindre");
		    peindre=true;
		    menu_principal_ou_fin();
		}
		if(creation_chaines){
		    if(d_ou_je_reviens==""){
			System.out.println("*** vers demarrer_application ");
			demarrer_application(false);
		    }else{
			System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
			second_ensemble_virtuel=false;
			if(d_ou_je_reviens=="Revenir a la page principale"){
			    d_ou_je_reviens="";
			    
			    demarrer_application(false);
			}else if(d_ou_je_reviens=="Recommencer"){
			    second_ensemble_virtuel=second_ensemble_virtuel_init;
			    vas_y_pour_les_chaines(i_demarre_dernier,false);
			}
		    }
		    if(n_ensembles==1){
			ensemble[0].setVisible(true);
			ensemble[0].paint_ensemble();
		    }
		    
		    //System.out.println(" apres creer_chaine");//System.out.println("relachee dans Applet"+relachee);
		}
		if(n_ensembles!=0){
		    double xmin, xmax;String st;
		    if(second_ensemble_virtuel&&!ensemble[0].stopper)
			ensemble[0].comment_init="";
		    for(int ii=0;ii<n_ensembles;ii++){
			//System.out.println("ii "+ii+" ensemble[ii].pressee "+ensemble[ii].pressee+" ensemble[ii].desactiver "+ensemble[ii].desactiver);
			ensemble[ii].deja_paint=false;
			if(!(ensemble[ii].pressee&&!ensemble[ii].trouve_deplacement)&&!ensemble[ii].desactiver&&!occupied){
			    if(ensemble[ii].commande_a_executer=="")
				ensemble[ii].run();
			    if(ensemble[ii].commande_a_executer!=""){
				ensemble[ii].command=ensemble[ii].commande_a_executer;
				ensemble[ii].commande_a_executer="";
				ensemble[ii].traite_commande();
			    }
			    if(ensemble[ii].le_virer){
				toutdebut=ensemble[ii].command=="Revenir a la page initiale avec infos";
				if(toutdebut){
				    demo_demarrer=false;
				    //terminer_demo=false;
				    //dejapaint=false;
				    creation_chaines=false;
				    eraserect(gr,0,0,1000,1600);
				}
				creation_chaines=true;
				second_ensemble_virtuel=false;
				peindre=true;
				relachee=false;
				pressee=false;
				draguee=false;
				cliquee=false;
				setVisible(true);
				choix_pas_chaine=false;
				choix_nb_elem=false;
				choix_masse_chaine=false;
				directement=false;	
				d_ou_je_reviens=ensemble[ii].command;
				System.out.println("*ii "+ii+" n_ensembles "+n_ensembles+" command "+ensemble[ii].command);
				if(comm!=null)
				    comm.elimine();
				n_ensembles=0;				
				eliminer(1-ii);
				eliminer(ii);
			    }
			    if(ensemble[ii]==null)
				break;
			//if(ensemble[ii].desactiver&&!ensemble[ii].deja_paint&&!occupied)
			    if(ensemble[ii]!=null)
				ensemble[ii].paint_ensemble();
			}
		    }
		    //if(!(ensemble[ii].pressee&&(ensemble[ii].command=="")))ensemble[ii].paint_ensemble();
		    //    System.out.println("n_ensembles "+n_ensembles);
		    peindre=false;
		}
	    }
	    i_run++;
	    if(i_run==20)
		i_run=0;
	    //System.out.println("isleep");
	    try {Thread.sleep(isleep);}
	    catch (InterruptedException signal){System.out.println("catch ");}
	    //System.out.println("run de nouveau");
	}
	
	menu_principal_ou_fin();
	for(int ii=0;ii<n_ensembles;ii++){
	    if(ensemble[ii].graphe_on)ensemble[ii].courbe.dispose();
	    if(ensemble[ii].fen_energie!=null)ensemble[ii].fen_energie.dispose();
	    if(comm!=null)comm.dispose();
	    ensemble[ii].dispose();
	    dispose();
	}
    }
    void eliminer(int num_ens){
	System.out.println("eliminer num_ens "+num_ens);
	if(ensemble[num_ens]!=null){
	    if(ensemble[num_ens].graphe_on){
		ensemble[num_ens].courbe.setVisible(false);
		ensemble[num_ens].courbe.dispose();
		ensemble[num_ens].graphe_on=false;
	    }
	    if(ensemble[num_ens].fen_energie!=null){
		ensemble[num_ens].fen_energie.setVisible(false);
		ensemble[num_ens].fen_energie.dispose();
	    }
	    ensemble[num_ens].dispose();
	    ensemble[num_ens].dispose();
	    ensemble[num_ens]=null;
	    if(ensemble[num_ens]!=null){
		ensemble[num_ens].dispose();
		ensemble[num_ens]=null;
	    }
	}
    }
    void peint_les_rectangles(){
	System.out.println(" entree peint_les rect");
	eraserect(gr, 0,0,120,1100);
	gr.setColor(Color.black);
	gr.drawString("# atoms "+nb_elem[0],left2, bot2+20);
	gr.drawString("0",left2-15, bot2);
	gr.drawString("200",right2+5, bot2);
	gr.drawString(""+nb_elem[0],(left2+right2)/2-5,top2-5);
	paintrect_couleur(gr,top2, left2, bot2, right2, Color.gray);
	//invertrect(gr,top2+1, left2+1, bot2-1, right2-1);
	int ppv=0;
	ppv=left2 +(int) Math.round((right2-left2)*(nb_elem[0]- min2)/(max2-min2));
	paintrect_couleur(gr,top2, left2, bot2, ppv, Color.black);
	gr.drawString("Distance entre atomes ",left0, bot0+20);
	gr.drawString("0",left0-15, bot0);
	gr.drawString("2e-10",right0+5, bot0);
	gr.drawString(""+(int)Math.round(pas_chaine[0]/1.e-11)+"e-11",(left0+right0)/2-5,top0-5);
	paintrect_couleur(gr,top0, left0, bot0, right0, Color.gray);
	//    invertrect(gr,top0+1, left0+1, bot0-1, right0-1);
	ppv=left0 +(int) Math.round((right0-left0)*(pas_chaine[0]-min0)/(max0-min0));
	paintrect_couleur(gr,top0, left0, bot0, ppv, Color.black);
	gr.drawString("0",left5-15, bot5);
	gr.drawString("100",right5+5, bot5);
	gr.drawString(""+(int)masse_chaine[0],(left5+right5)/2-5,top5-5);
	gr.drawString("Masse des atomes (H=1)",left5, bot5+20);
	paintrect_couleur(gr,top5, left5, bot5, right5, Color.gray);
	//    invertrect(gr,top5+1, left5+1, bot5-1, right5-1);
	ppv=left5 +(int) Math.round((right5-left5)*(masse_chaine[0]-min5)/(max5-min5));
	// System.out.println("masse_chaine "+masse_chaine+"min5"+min5+"max5"+max5+"ppv"+ppv);
	paintrect_couleur(gr,top5, left5, bot5, ppv, Color.black);
    }
    public  void menu_principal_ou_fin(){
      if(run_applet){
	  if(creation_chaines){
	      System.out.println(" peint le menu "+" cliquee "+cliquee);
	      gr.setColor(Color.red);
	      gr.setFont(times_gras_24);
	      gr.drawString("Vous pouvez modifier les parametres par defaut en cliquant ci dessus ",left_demarre, top_demarre-40);	      
	      gr.drawString("Puis cliquez dans ce menu.",left_demarre, top_demarre-20);	      
	      paintrect_couleur(gr,top_demarre,left_demarre,bottom_demarre,right_demarre,Color.red);
	      gr.setFont(times_gras_14);
	      for(int i=0;i<=10;i++){
		  gr.drawString(string_menu[i],left_demarre+10, top_demarre+14+i*20);
		  drawline_couleur(gr,left_demarre,top_demarre+i*20, right_demarre,top_demarre+i*20,Color.red);
	      }
	      peint_les_rectangles();
	      gr.setFont(times14);
	      cliquee=false;
	      peindre=false;
	      System.out.println(" fin peint le menu "+" cliquee "+cliquee);
	      //if(d_ou_je_reviens=="Revenir a la page principale")
	      //  ensemble[1000]=null;
	  }
      }else{
	  eraserect( gr,0,0,1000,1100);
	  gr.setFont(times_gras_24);gr.setColor(Color.black);
	  if(temps_en_sec-temps_initial_en_secondes>temps_minimum)
	      gr.drawString("Timeout",100,100);
	  else{
	      for(int i=0;i<20;i++){
		  gr.drawString("FIN DU PROGRAMME",100,100);
	      }
	  }
      }
      //System.out.println("entree dans paint");
    }
      
  void paintcircle_couleur (Graphics g,int x,int y, int r,Color couleur)
    
    {
      g.setColor(couleur);g.fillOval(x,y,r,r);
    }
    
  void drawline_couleur(Graphics g,int xin, int yin, int xfin, int yfin, Color couleur){
      if(g!=null&&couleur!=null){
	  g.setColor(couleur);
	  g.drawLine(xin,yin,xfin,yfin);
      }
    }
    
    void paintrect_couleur(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.drawRect(x,y,width,height);
    }    
    void remplisrect(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.fillRect(x,y,width,height);
    }    
    
    void paintrect(Graphics g,int top, int left, int bot, int right)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(Color.black);g.drawRect(x,y,width,height);
    }
    void paintcircle(Graphics g,int xx, int yy, int rr)
      
    {int x,y,r;
    x=xx;y=yy;r=rr;
    paintcircle_couleur(g,x,y,r,Color.blue);
    }
    
    void invertrect(Graphics g,int top, int left, int bot, int right)
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    //g.setColor(Color.black);g.fillRect(x,y,width,height);
    }
    
    void eraserect(Graphics g, int top, int left, int bot, int right)
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(Color.white);g.fillRect(x,y,width,height);
    }
    void demarrer_application(boolean demo){ 
	directement=false;
	if(cliquee){
	    int xi=ppmouseh;int yi=ppmousev;
	    System.out.println("kkkkkkkkkk cliquee "+cliquee+" xi "+xi+" yi "+yi);
	    if (yi>top0&&yi<bot0&&xi>left0&&xi<right0){
		System.out.println("22");
		pas_chaine[0]=min0+(xi-left0)*(max0-min0)/(right0-left0);
		pas_chaine[0]=Math.round(pas_chaine[0]/1e-11)*1e-11;
		pas_chaine[1]=pas_chaine[0];
		System.out.println("pas_chaine "+ pas_chaine[0]);peindre=true;
		choix_pas_chaine=true;
		peint_les_rectangles();
	    }else if (yi>top2&&yi<bot2&&xi>left2&&xi<right2){
		//System.out.println("33");
		nb_elem[0]=Math.round(min2+(xi-left2)*(max2-min2)/(right2-left2));
		nb_elem[1]=nb_elem[0];
		comment="atome trouve "+ nb_elem[0];peindre=true;
		System.out.println("nb_elem "+ nb_elem[0]);
		choix_nb_elem=true;
		peint_les_rectangles();
	    }else if (yi>top5&&yi<bot5&&xi>left5&&xi<right5){
		//System.out.println("44");
		masse_chaine[0]=Math.round(min5+(xi-left5)*(max5-min5)/(right5-left5));
		masse_chaine[1]=masse_chaine[0];
		peindre=true;
		System.out.println("masse_chaine "+ masse_chaine[0]);
		choix_masse_chaine=true;
		peint_les_rectangles();
	    }else if ((xi>left_demarre)&&(xi<right_demarre&&yi>top_demarre&&yi<top_demarre+220)){
		directement=true;
		xi=ppmouseh;yi=ppmousev;
	    }
	    cliquee=false;
	    System.out.println("11");
	    if(directement){
		if ((xi>left_demarre)&&(xi<right_demarre&&yi>top_demarre&&yi<top_demarre+220)){
		    for(int i=0;i<=10;i++){
			if ((yi>top_demarre+i*20)&&(yi<top_demarre+(i+1)*20)){
			    System.out.println(" i trouvé "+" i "+i);		    
			    second_ensemble_virtuel=false;
			    System.out.println("i "+i+ " yi "+yi);
			    if(!choix_nb_elem){
				if(i==8)nb_elem[0]=50;
				if((i>0)&&(i<8))nb_elem[0]=30;
			    }
			    if(i==9)nb_elem[0]=2;if(i==10)nb_elem[0]=3;
			    if(i==0||i==2||i==3){
				if(!choix_nb_elem)
				    nb_elem[0]=200;
				if(!choix_pas_chaine)
				    if(i==3)
					pas_chaine[0]=0.15e-10;
				    else
					pas_chaine[0]=0.3e-10;
			    }else
				if(!choix_pas_chaine)	
				    pas_chaine[0]=1.0e-10;
			    pas_chaine[1]=pas_chaine[0];
			    nb_elem[1]=nb_elem[0];
			    if(!choix_masse_chaine)
				i_demarre_dernier=i;
			    second_ensemble_virtuel_init=second_ensemble_virtuel;
			    vas_y_pour_les_chaines(i,demo);
			}
		    }
		}
	    }		
	}
    }
    void vas_y_pour_les_chaines(int i,boolean demo){
	n_ensembles=2;
	d_ou_je_reviens="";
	System.out.println(" vas_y_pour_les_chaines "+" i "+i);
	d_ou_je_reviens="";
	for(int iii=0;iii<n_ensembles;iii++)
	    creation_d_un_ensemble(iii,i,demo);
	relachee=false;pressee=false;
	creation_chaines=false;
	peindre=true;
	//if(comm!=null)comm.elimine();
    }

    public void creation_d_un_ensemble(int iii,int i_demarre,boolean demo){
	System.out.println("iii "+iii);
	if(ensemble[iii]!=null){
	    ensemble[iii].dispose();
	    ensemble[iii]=null;    ;
	}
	ensemble[iii]=new ensemble_de_chaines(nb_elem[iii],dimensionpixel,pas_chaine[iii],masse_chaine[iii],"chaine "+iii,this,iii,i_demarre,right_chaine,demo);
    }
    public void calcule_coefficients(){	
	System.out.println(" ensemble[0].el[10].yreel "+ensemble[0].el[10].yreel);
	for (int i=0;i<ensemble[0].nb_el_ens;i++){
	    coef_classes[i]=0.;
	    mode[i]=0;
	}
	for (int i=1;i<ensemble[0].nb_el_ens;i++){
	    coeff[i]=0.;
	    for (int j=0;j<ensemble[0].nb_el_ens;j++){
		if((i==1)&&(j==10))System.out.println(" ensemble[0].el[j].yreel "+ensemble[0].el[j].yreel+" ensemble[0].nb_el_ens "+ensemble[0].nb_el_ens+" pas_chaine "+pas_chaine);
		coeff[i]+=ensemble[0].el[j].yreel*Math.sin(i*j*pi/(ensemble[0].nb_el_ens-1));
	    }
	    coef_classes[i]=coeff[i];
	    mode[i]=i;
	    if(i>1)
		if(Math.abs(coef_classes[i])>Math.abs(coef_classes[i-1])){
		    double c_c=coef_classes[i-1];
		    coef_classes[i-1]=coef_classes[i];
		    coef_classes[i]=c_c;
		    int im=mode[i-1];mode[i-1]=mode[i];mode[i]=im;
		}
	    if(i>2)
		if(Math.abs(coef_classes[i-1])>Math.abs(coef_classes[i-2])){
		    double c_c=coef_classes[i-2];
		    coef_classes[i-2]=coef_classes[i-1];
		    coef_classes[i-1]=c_c;
		    int im=mode[i-2];mode[i-2]=mode[i-1];mode[i-1]=im;
		}
	    if(i<=3)System.out.println("i "+i+" coeff[i] "+ coeff[i]+" mode[i] "+mode[i]);
	}
	System.out.println(" coef_classes[1] "+ coef_classes[1]+" coef_classes[1] "+ coef_classes[2]+" coef_classes[3] "+ coef_classes[3]);
	for (int iq=0;iq<ensemble[1].nb_el_ens;iq++){
	    ensemble[1].el[iq].yreel=2*coef_classes[1]*Math.sin(mode[1]*pi*iq/(ensemble[0].nb_el_ens-1))/(ensemble[0].nb_el_ens-1);
	    ensemble[1].el[iq+ensemble[0].nb_el_ens].yreel=2*coef_classes[2]*Math.sin(mode[2]*pi*iq/(ensemble[0].nb_el_ens-1))/(ensemble[0].nb_el_ens-1);
	    ensemble[1].el[iq+2*ensemble[0].nb_el_ens].yreel=2*coef_classes[3]*Math.sin(mode[3]*pi*iq/(ensemble[0].nb_el_ens-1))/(ensemble[0].nb_el_ens-1);
	    if(iq==10)System.out.println(" ensemble[1].el[10].yreel "+(float)ensemble[1].el[iq].yreel+" "+(float)ensemble[1].el[10+ensemble[0].nb_el_ens].yreel+" "+(float)ensemble[1].el[10+2*ensemble[0].nb_el_ens].yreel);
	    ensemble[0].el[iq].yreel=ensemble[1].el[iq].yreel+ensemble[1].el[iq+ensemble[1].nb_el_ens].yreel+ensemble[1].el[iq+2*ensemble[1].nb_el_ens].yreel;
	    if(iq==10)System.out.println(" ensemble[0].el[10].yreel "+(float)ensemble[0].el[iq].yreel);
	}
	//	ensemble[1].el[1000]=null;
    }
    public void	traite_click(){
      //System.out.println("entree traite_click ");
	while(occupied){
	    System.out.println("*traite_click programe principal cliquee "+cliquee);
	}
	if(d_ou_je_reviens!=""){
	    cliquee=false;
	    pressee=false;
	    relachee=false;
	}
	if(cliquee){
	    Date maintenant=new Date();
	    temps_initial_en_secondes=temps_en_secondes(maintenant);
	}
  }
    
    public void appelle_comm(String command,int i_dem){
	if(comm==null){
	    comm=new commentaire(command,i_dem);
	}
	else
	    comm.ecrit_aide();
    }
    class commentaire extends Frame{
	final int top=150;final int left=450;final int bottom=600;final int right=970;Graphics grp_c;
	Image imag;
	Graphics hTTampon;
	String name[]=new String [12];
	int i_demarre;int nb_lignes=10; 
	public commentaire(String s,int i_demarre1){
	    super(s);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			comm.elimine();
			comm=null;
		    };
		});
	    i_demarre=i_demarre1;
	    System.out.println("cree "+s+" i_demarre "+i_demarre );
	    name[0]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_0.jpg";    
	    name[1]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_1.jpg";    
	    name[2]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_2.jpg";    
	    name[3]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_3.jpg";    
	    name[4]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_4.jpg";    
	    name[5]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_5.jpg"; 
	    name[6]=name[5];
	    name[7]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_7.jpg";    
   	    name[8]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_8.jpg";    
	    name[9]="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/conseils_chaine_9.jpg";    
	    name[10]=name[9];
   
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    //setLocation(left,top);
	    setVisible(true);
	    grp_c=getGraphics();
	    imag=createImage(600,400);
	    hTTampon=imag.getGraphics();
	    imag=Toolkit.getDefaultToolkit().getImage(name[i_demarre]);
	    MediaTracker tracker=new MediaTracker(this);
	    tracker.addImage(imag,0); 
	    try {tracker.waitForID(0); }
	    catch (InterruptedException e) {
		System.out.println(" image pas arrivee?");
	    }
	    grp_c.drawImage(imag,20,50,null);
	}
	public void ecrit_aide(){
	    grp_c.drawImage(imag,20,50,null);
	}
	public void elimine(){
	    System.out.println("elimination de comm");
	    comm=null;  dispose();
	}
    }
  class MouseStatic extends MouseAdapter{
    appli_chn_datomes subject;
    public MouseStatic (appli_chn_datomes a){
      subject=a;
    }
    public void mouseClicked(MouseEvent e){
       ppmouseh=e.getX();ppmousev=e.getY();
       cliquee=true;
	System.out.println("$$$$$$cliquee "+cliquee);
	traite_click();
	//	System.out.println("ensemble[ichaine].nb_el_ens "+ensemble[ichaine].nb_el_ens);
	//System.out.println("ichaine "+ichaine);
	//for( int iq=0;iq<ensemble[ichaine].nb_el_ens;iq++)
	//ensemble[ichaine].el[iq].deplacer=false;
    }
    public void mousePressed(MouseEvent e){
      ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	System.out.println("$$$$$$pressee "+pressee);
	traite_click();
    }
    public void mouseReleased(MouseEvent e){
      ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
      System.out.println("$$$$$relachee "+relachee);
      traite_click();
    }
  }

  
}

class ensemble_de_chaines extends Frame implements ActionListener {
    static final int 	top=60,left=10,bottom=160,right=580,top0=52,left0=500,bot0=62,right0=700,top2=252,left2=60,right2=140,bot2=262,top5=52,left5=40,bot5=62,right5=120;
    int topa=10,lefta=10,bottoma=310;boolean le_virer=false;
    static String nb_demies_longueurs_d_onde_[]=new String[60];
    static String nb_quarts_longueurs_d_onde_[]=new String[60];
    MenuItem nb_demies_longueurs_d_onde[]=new MenuItem[60];
    MenuItem nb_quarts_longueurs_d_onde[]=new MenuItem[60];
    static MenuItem menu_deph[]=new MenuItem[5];
    static String menu_deph_[]=new String[5];
    static MenuItem menu_ret[]=new MenuItem[5];
    static String menu_ret_[]=new String[5];
    static final double pi=3.141592652, qat=1.6e-19,eps00=1.0/(36*pi*1.0E9);
    int n_print=0,nb_de_vrais_pas=0;boolean vas_y_run=true;
    boolean trouve_deplacement=false,ret,fin_gere_menus_souris;
    boolean trois_zones=false,onde_stat=false;
    boolean dephasons=false,retardons=false;
    double dx_pt_souris, dy_pt_souris,xee,yee,xrepo_dep,phase=0.,phase_bis=0,dephasage=0.;
    double dtt=0.;
    static double facteur_fleche[]=new double[15];
    static double ampl_bis=0.,ampl=0.;
    static int n_passages=0,npassages_retard=0;
    String command_prec;boolean debut=true,fixer_element=false;
    Graphics grph;int n_stoppages;
    int xdessin1=0, ydessin1=0; 
    boolean cree=false;int iii_dep,iq_dep=0;double xxxx,yyyy;
    boolean graphe_on=false,pas_a_pas=false,deja_paint=false;
    double f_norme; int x_init,y_init;boolean amortissement_critique;
    double fmax2,fmin2;int nombens;
    int y_fleche=0,add_fleche=0,jadd_fleche=0;
    int ppmouseh;int ppmousev;boolean relachee,pressee,cliquee,draguee;
    element el[]=new element[400];double masse_totale;boolean	chainecreee;
    int xrepos,yrepos,iadd_yrepos=0,yinitial,achoisi;
    int n_chaines;int aaa;int numer;
    int nb_el_ens;boolean aaaaa;int nb_el[]=new int[2];
    int n_demi,identite_menu,n_quart; boolean dg;

    double Amplitude_maximale,raideur,pas1;
    int vasadroite;int zoom_x=1,zoom_y=1;double facx=1.,facy=1.;
    boolean graphe=false,draw_cdm=false,tire_la_chaine=false;
    int atome_glisse;boolean comparaison=false;
    boolean atomefixe;int iprepare;
    public boolean oscillations_transverses,oscillations_forcees_sinusoidales;
    boolean  oscil_composees, oscil_paquet;
    boolean stopper,fixer_cdm;boolean aider=false;
    double ffrr;double distance_inter_atomes,masse_atomes;
    int top_chaine, left_chaine, bottom_chaine, right_chaine;
    double   frequence0 ,frequence ,frequence_centrale;
    double  frequence_min,frequence_max;
    String comment="";graphique courbe;boolean desactiver;
    Graphics gTampon,gTampon0;Image crop,crop0;
    Image crop_part_red,crop_part_black,crop_part_blue;
    Graphics gTampon_part_red,gTampon_part_black,gTampon_part_blue;
    int ray=4;
    //private TextField tf;private keyList kl;
    String command,commande_a_executer;
    appli_chn_datomes subject;double lambda;int ij;double dimensionpixel;
    boolean key_entered;int i_demarre;boolean ne_plus_calc_0;int numero_ensemble;
    private MouseMotion motion;String comment_init;boolean ajouter_osc,frotter;
    graphique fen_energie;
    private MouseStatic mm; 	MenuBar mb1[]=new MenuBar[2];
    boolean demonst=false;
    boolean ne_plus_calc_dernier=false,ajouter_osc_dernier=false,oscillations_forcees_sinusoidales_dernier=false;

    public ensemble_de_chaines(String s){
	System.out.println("constructeur vide "+s);
}
    public ensemble_de_chaines(int nb_el0,double dimensionpixel1,double distance_inter_atomes1,double masse_atomes1,String s, appli_chn_datomes a,int numero_ensemble1, int i_demarre1,int righta,boolean demo){
	super(s);subject=a;i_demarre=i_demarre1;
	demonst=demo;
	if(!demonst)
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			le_virer=true;
			command="Revenir a la page principale";
		    };
		});
 	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this); setBackground(Color.white);
	this.addMouseMotionListener(motion);
	aaa=0;nb_el_ens=nb_el0;distance_inter_atomes=distance_inter_atomes1;masse_atomes=masse_atomes1;numero_ensemble=numero_ensemble1;
	stopper=false;desactiver=false;iprepare=0;fixer_element=false;
	n_chaines=1;ffrr=0.0;ajouter_osc=false;
	top_chaine=topa+numero_ensemble*300;left_chaine=lefta;bottom_chaine=bottoma+numero_ensemble*300;
	right_chaine=righta;
	System.out.println("top_chaine "+top_chaine+" left_chaine "+left_chaine+" bottom_chaine "+bottom_chaine+" right_chaine "+right_chaine);

	f_norme=2.0e-10;dimensionpixel=dimensionpixel1;
	amortissement_critique=false;ne_plus_calc_0=false;
	masse_totale=0;fmax2=3.0e-13;fmin2=0.0;
	Amplitude_maximale=50.0;
	facteur_fleche[-1+1]=4.;
	facteur_fleche[0+1]=0.5;
	facteur_fleche[1+1]=4.;
	facteur_fleche[2+1]=1.;
	facteur_fleche[3+1]=0.05;
	facteur_fleche[4+1]=2.;
	facteur_fleche[5+1]=4.;
	facteur_fleche[6+1]=4.;
	facteur_fleche[7+1]=4.;
	facteur_fleche[8+1]=2.;
	facteur_fleche[9+1]=1.;
	facteur_fleche[10+1]=1.;	
	x_init=20;y_init=150;
	command="";commande_a_executer="";achoisi=-1;frotter=false;
	setTitle(s);
	oscil_composees=false;oscil_paquet=false;draw_cdm=false;
	fixer_cdm=false;
	oscillations_transverses=true;phase=0.0;

	if(i_demarre1==0||i_demarre1==2){
	    zoom_x=0;facx=0.5;
	}
	vasadroite=0;graphe=false;
	//frequence0=1./(2*pi*Math.sqrt((4*pi*eps00*masse_atomes/(6.0*1.0e26)*distance_inter_atomes*distance_inter_atomes*distance_inter_atomes))/qat);
	frequence0=1./(2*pi*Math.sqrt((4*pi*eps00*20./(6.0*1.0e26)*distance_inter_atomes*distance_inter_atomes*distance_inter_atomes))/qat);
	frequence0*=3;
	frequence=frequence0;frequence_centrale=frequence;
	System.out.println("frequence_centrale "+frequence_centrale);
	if(i_demarre<=1||i_demarre==3){
	    //pas_a_pas=true;
	    ajouter_osc=true;
	    oscillations_forcees_sinusoidales=false;
	}
	dtt=1./(frequence*300);
	if(i_demarre==0&&numero_ensemble==1){
		System.out.println("&&&dtt "+(float)dtt+" frequence "+(float)frequence+" dtt*frequence "+(float)(dtt*frequence)+" subject.ensemble[0].dtt "+(float)subject.ensemble[0].dtt+" subject.ensemble[0].frequence "+(float)subject.ensemble[0].frequence+" subject.ensemble[0].dtt*subject.ensemble[0].frequence "+(float)(subject.ensemble[0].dtt*subject.ensemble[0].frequence));
	}
	if(i_demarre==2){
	    oscillations_forcees_sinusoidales=true;
	    //pas_a_pas=true;
	}
	if(i_demarre==7||i_demarre==8)
	    pas_a_pas=true;
	n_stoppages=0;
	frequence_min=1.0e12;frequence_max=1.0e14;
	raideur=0.2*qat*qat/(4.0 *pi*eps00)/(distance_inter_atomes*distance_inter_atomes*distance_inter_atomes);
	System.out.println("raideur "+raideur+" masse_atomes "+masse_atomes);


	//	if(numero_ensemble==0)
	//  {
	if(!(subject.second_ensemble_virtuel&&i_demarre==7&&numero_ensemble==1))
		mb1[numero_ensemble]=null;


	pack();setVisible(true);
	nb_demies_longueurs_d_onde_[0]="0";
	nb_demies_longueurs_d_onde_[1]="1";
	nb_demies_longueurs_d_onde_[2]="2";
	nb_demies_longueurs_d_onde_[3]="3";
	nb_demies_longueurs_d_onde_[4]="4";
	nb_demies_longueurs_d_onde_[5]="5";
	nb_demies_longueurs_d_onde_[6]="6";
	nb_demies_longueurs_d_onde_[7]="7";
	nb_demies_longueurs_d_onde_[8]="8";
	nb_demies_longueurs_d_onde_[9]="9";
	nb_demies_longueurs_d_onde_[10]="10";
	nb_demies_longueurs_d_onde_[11]="11";
	nb_demies_longueurs_d_onde_[12]="12";
	nb_demies_longueurs_d_onde_[13]="13";
	nb_demies_longueurs_d_onde_[14]="14";
	nb_demies_longueurs_d_onde_[15]="15";
	nb_demies_longueurs_d_onde_[16]="16";
	nb_demies_longueurs_d_onde_[17]="17";
	nb_demies_longueurs_d_onde_[18]="18";
	nb_demies_longueurs_d_onde_[19]="19";
	nb_demies_longueurs_d_onde_[20]="20";
	nb_demies_longueurs_d_onde_[21]="21";
	nb_demies_longueurs_d_onde_[22]="22";
	nb_demies_longueurs_d_onde_[23]="23";
	nb_demies_longueurs_d_onde_[24]="24";
	nb_demies_longueurs_d_onde_[25]="25";
	nb_demies_longueurs_d_onde_[26]="26";
	nb_demies_longueurs_d_onde_[27]="27";
	nb_demies_longueurs_d_onde_[28]="28";
	nb_demies_longueurs_d_onde_[29]="29";
	for (int j=0;j<30;j++)
	    nb_demies_longueurs_d_onde[j]=new MenuItem(nb_demies_longueurs_d_onde_[j]);
	nb_quarts_longueurs_d_onde_[1]=" 1";
	nb_quarts_longueurs_d_onde_[3]=" 3";
	nb_quarts_longueurs_d_onde_[5]=" 5";
	nb_quarts_longueurs_d_onde_[7]=" 7";
	nb_quarts_longueurs_d_onde_[9]=" 9";
	nb_quarts_longueurs_d_onde_[11]=" 11";
	nb_quarts_longueurs_d_onde_[13]=" 13";
	nb_quarts_longueurs_d_onde_[15]=" 15";
	nb_quarts_longueurs_d_onde_[17]=" 17";
	nb_quarts_longueurs_d_onde_[19]=" 19";
	nb_quarts_longueurs_d_onde_[21]=" 21";
	nb_quarts_longueurs_d_onde_[23]=" 23";
	nb_quarts_longueurs_d_onde_[25]=" 25";
	nb_quarts_longueurs_d_onde_[27]=" 27";
	nb_quarts_longueurs_d_onde_[29]=" 29";
	nb_quarts_longueurs_d_onde_[31]=" 31";
	nb_quarts_longueurs_d_onde_[33]=" 33";
	nb_quarts_longueurs_d_onde_[35]=" 35";
	nb_quarts_longueurs_d_onde_[37]=" 37";
	nb_quarts_longueurs_d_onde_[39]=" 39";
	nb_quarts_longueurs_d_onde_[41]=" 41";
 	nb_quarts_longueurs_d_onde_[43]=" 43";
	nb_quarts_longueurs_d_onde_[45]=" 45";
	nb_quarts_longueurs_d_onde_[47]=" 47";
	nb_quarts_longueurs_d_onde_[49]=" 49";
	nb_quarts_longueurs_d_onde_[51]=" 51";
	nb_quarts_longueurs_d_onde_[53]=" 53";
	nb_quarts_longueurs_d_onde_[55]=" 55";
	nb_quarts_longueurs_d_onde_[57]=" 57";
	nb_quarts_longueurs_d_onde_[59]=" 59";
	for (int j=1;j<61;j+=2)
	    nb_quarts_longueurs_d_onde[j]=new MenuItem(nb_quarts_longueurs_d_onde_[j]);
	menu_deph_[0]="   0";
	menu_deph_[1]="Pi/2";
	menu_deph_[2]="Pi";
	menu_deph_[3]="3*Pi/2";
	menu_deph_[4]="2*Pi";
	menu_ret_[0]="0";
	menu_ret_[1]="50";
	menu_ret_[2]="100";
	menu_ret_[3]="150";
	menu_ret_[4]="200";
	for (int j=0;j<5;j++){
	    menu_deph[j]=new MenuItem(menu_deph_[j]);
	    menu_ret[j]=new MenuItem(menu_ret_[j]);
	}


	if(!(subject.second_ensemble_virtuel&&i_demarre==7&&numero_ensemble==1))
	    comment_init="Vous pouvez deplacer les atomes a la souris et utiliser les menus.";
	else if(i_demarre!=1)
	    comment_init="Vous ne pouvez pas deplacer les atomes a la souris ni utiliser le menu.";
	for (int j=0;j<nb_el_ens;j++){
	    cree_element(j,masse_atomes);
	    //		System.out.println("masse_totale,masse_atomes"+masse_totale+masse_atomes);
	    masse_totale=masse_totale+masse_atomes;
	    xrepos=el[j].xc_premier_element;
	    yrepos=el[j].yc_premier_element;
	    yinitial=yrepos;
	}
	iadd_yrepos=0;
	if(i_demarre==2||i_demarre==7){
	    subject.second_ensemble_virtuel=true;
	    int nnbb=3*nb_el_ens;
	    if(i_demarre==2)
		nnbb=2*nb_el_ens;
	    if(numero_ensemble==1)
		for (int j=nb_el_ens;j<nnbb;j++){
		    if(i_demarre==2)
			iadd_yrepos=5;
		    cree_element(j,masse_atomes);
		}
	}
	chainecreee=true;
	init_cond(true,true);
	setSize(right_chaine-left_chaine,bottom_chaine-top_chaine+10);
	setLocation(left_chaine,top_chaine);
	setVisible(false);setVisible(true);

        grph=getGraphics();

	crop=createImage(right_chaine-left_chaine,bottom_chaine-top_chaine);
	gTampon=crop.getGraphics();
	if(i_demarre!=-1)
	    gTampon.setFont(subject.times_gras_24);
	else
	    gTampon.setFont(subject.times_gras_14);
	crop0=createImage(right_chaine-left_chaine,bottom_chaine-top_chaine);
	gTampon0=crop0.getGraphics();
	crop_part_red=createImage(2*ray,2*ray);
	//System.out.println("i "+i+" ir "+ir+" ray "+ray+" crop_part_red "+crop_part_red);
	gTampon_part_red=crop_part_red.getGraphics();
	gTampon_part_red.setColor(Color.red);
	gTampon_part_red.fillOval(0,0,2*ray,2*ray);
	crop_part_black=createImage(2*ray,2*ray);
	//System.out.println("i "+i+" ir "+ir+" ray "+ray+" crop_part_black "+crop_part_black);
	gTampon_part_black=crop_part_black.getGraphics();
	gTampon_part_black.setColor(Color.black);
	gTampon_part_black.fillOval(0,0,2*ray,2*ray);
	crop_part_blue=createImage(2*ray,2*ray);
	//System.out.println("i "+i+" ir "+ir+" ray "+ray+" crop_part_blue "+crop_part_blue);
	gTampon_part_blue=crop_part_blue.getGraphics();
	gTampon_part_blue.setColor(Color.blue);
	gTampon_part_blue.fillOval(0,0,2*ray,2*ray);
	
	//if(!demo){
	//System.out.println("top_chaine "+top_chaine+" left_chaine "+left_chaine+" bottom_chaine "+bottom_chaine+" right_chaine "+right_chaine);
	grph.drawImage(crop_part_red,150,150,null);
	grph.drawImage(crop_part_black,160,160,null);
	grph.drawImage(crop_part_blue,170,170,null);
	//el[1000]=null;
	//}
	if(i_demarre==2||i_demarre==4)
	    oscillations_forcees_sinusoidales=true;
	if(i_demarre==1&&numero_ensemble==1||i_demarre==2||i_demarre==3)
	    amortissement_critique=true;
	if((i_demarre==5)||(i_demarre==6)){
	    oscillations_forcees_sinusoidales=false;
	    onde_stat=true;
	    if(i_demarre==5){
		n_quart=3+numero_ensemble*4;
		lambda=4*( nb_el_ens-0.5)* distance_inter_atomes/ n_quart;
		comment_init=""+n_quart+" quarts de longueur d'onde";
	    }else{
		el[nb_el_ens-1].fixe=true;
		n_demi=2+numero_ensemble*4;
		comment_init=""+n_demi+" demies longueurs d'onde";
		lambda=2*(nb_el_ens -1)* distance_inter_atomes/n_demi;
	    }
	    demarre_onde_stationnaire();
	    
	    //traite_commande ();
	}
	if(i_demarre==7){
	    oscillations_forcees_sinusoidales=false;
	    if(numero_ensemble==1){
		comment="Vous avez ici les trois plus fortes ondes stationnaires";
	    }else{
		comment_init="Deplacez un atome a la souris, les autres suivront.";
		comment="En dessous, vous avez la decomposition en trois ondes stationnaires";
	    }
	    if(numero_ensemble!=1){
		el[nb_el_ens-1].fixe=true;
		if(numero_ensemble==0)
		    el[0].fixe=true;
	    }else{
		subject.appelle_comm("aide et commentaires",i_demarre);
		el[nb_el_ens].fixe=subject.ensemble[0].el[0].fixe; 
		el[2*nb_el_ens].fixe=subject.ensemble[0].el[0].fixe; 
		el[nb_el_ens-1].fixe=subject.ensemble[0].el[nb_el_ens-1].fixe; 
		el[2*nb_el_ens-1].fixe=subject.ensemble[0].el[nb_el_ens-1].fixe; 
		el[3*nb_el_ens-1].fixe=subject.ensemble[0].el[nb_el_ens-1].fixe;
	    }
	    if(numero_ensemble==0)
		commande_a_executer="tirer de nouveau sur la chaine";
	}
	if(i_demarre==8){
	    oscillations_forcees_sinusoidales=false;
	    subject.appelle_comm("aide et commentaires",i_demarre);
	    zoom_x=0;facx=0.5;
	    double ampl=2.*Amplitude_maximale*dimensionpixel;
	    if(numero_ensemble==0){
		el[20].yreel=ampl*0.05;
		el[21].yreel=ampl*0.15;
		el[22].yreel=ampl*0.3;
		el[23].yreel=ampl*0.5;
		el[24].yreel=ampl*0.7;
		el[25].yreel=ampl*0.85;
		el[26].yreel=ampl*0.95;
		el[27].yreel=ampl;
		el[28].yreel=ampl*0.95;
		el[29].yreel=ampl*0.85;
		el[30].yreel=ampl*0.7;
		el[31].yreel=ampl*0.5;
		el[32].yreel=ampl*0.3;
		el[33].yreel=ampl*0.15;
		el[34].yreel=ampl*0.05;
	    }else{
		el[nb_el_ens-1].fixe=true;
		el[24].yreel=ampl*0.2;
		el[25].yreel=ampl*0.4;
		el[26].yreel=ampl*0.8;
		el[27].yreel=ampl;
		el[28].yreel=ampl*0.8;
		el[29].yreel=ampl*0.4;
		el[30].yreel=ampl*0.2;
	    }
	}
	if(i_demarre>8){
	    comment="Deplacez les atomes avec la souris ou utiliser les menus.";
	    oscillations_transverses=false;
	    el[0].fixe=false;
	    vasadroite=200;	
	    zoom_x=3;facx=4.;	
	}
	ne_plus_calc_dernier=ne_plus_calc_0;
	ajouter_osc_dernier=ajouter_osc;
	oscillations_forcees_sinusoidales_dernier=oscillations_forcees_sinusoidales;
	barre_des_menus();	
    }

    public void barre_des_menus(){
	if(!(subject.second_ensemble_virtuel&&(i_demarre==7||i_demarre==2)&&numero_ensemble==1)){
	    System.out.println("i_demarre  "+i_demarre);
	    mb1[numero_ensemble]=new MenuBar();
	    MenuItem item1=new MenuItem("sur x, facteur 2");
	    MenuItem item2=new MenuItem("sur x, facteur 0.5");
	    MenuItem item3=new MenuItem("sur y, facteur 2");
	    MenuItem item4=new MenuItem("sur y, facteur 0.5");
	    MenuItem item5=new MenuItem("translation vers la droite");
	    MenuItem item6=new MenuItem("translation vers la gauche");
	    Menu affinite=new Menu("zoom");
	    affinite.add(item1);
	    item1.addActionListener(this);
	    affinite.add(item2);
	    item2.addActionListener(this);
	    affinite.add(item3);
	    item3.addActionListener(this);
	    affinite.add(item4);
	    item4.addActionListener(this);
	    if(i_demarre>=9){
		affinite.add(item5);
		item5.addActionListener(this);
		affinite.add(item6);
		item6.addActionListener(this);
	    }
	    mb1[numero_ensemble].add(affinite);
	    
	    System.out.println("subject.second_ensemble_virtuel "+subject.second_ensemble_virtuel+" numero_ensemble "+numero_ensemble);
	    if(demonst){
		item1.setEnabled(false);
		item2.setEnabled(false);
		item3.setEnabled(false);
		item4.setEnabled(false);
		item5.setEnabled(false);
		item6.setEnabled(false);
	    }
	    Menu menupas_a_pas=new Menu("Pas_A_Pas");
	    MenuItem iteq1=new MenuItem("pas_a_pas");
	    if(pas_a_pas)
		iteq1=new MenuItem("pas pas_a_pas");  
	    menupas_a_pas.add(iteq1);
	    if(demonst)
		iteq1.setEnabled(false);
	    iteq1.addActionListener(this);
	    mb1[numero_ensemble].add(menupas_a_pas);
	    if(i_demarre==2){
		Menu menu_retard=new Menu("Dephasage");
		Menu menu_dephh=new Menu("Dephasage");
		MenuItem menu_deph0=new Menu("   0");
		for (int j=0;j<5;j++){
		    menu_dephh.add(menu_deph[j]);
		    menu_deph[j].addActionListener(this);
		}
		menu_retard.add(menu_dephh);
		Menu menu_rett=new Menu("Retard");
		for (int j=0;j<5;j++){
		    menu_rett.add(menu_ret[j]);
		    menu_ret[j].addActionListener(this);
		}
		menu_retard.add(menu_rett);
		mb1[numero_ensemble].add(menu_retard);
	    }
	    if(nb_el_ens<=4){
		Menu osc_entretenues=new Menu("Type d'Oscillations");
		MenuItem item11=new MenuItem("oscillations transverses ");
		MenuItem item22=new MenuItem("oscillations longitudinales");
		osc_entretenues.add(item11);
		item11.addActionListener(this);
		osc_entretenues.add(item22);
		item22.addActionListener(this);
		if(demonst){
		    item11.setEnabled(false);
		    item22.setEnabled(false);
		}
		mb1[numero_ensemble].add(osc_entretenues);
	    }
	    
	    Menu modifs=new Menu("modifier");
	    MenuItem itep1=new MenuItem("fixer ou liberer un element");
	    MenuItem itep2=new MenuItem("fixer_le_cdm");
	    //MenuItem itep3=new MenuItem("changer de ref. cdm/labo pour le dessin");
	    MenuItem itep4=new MenuItem("tirer de nouveau sur la chaine");
	    MenuItem itep6=new MenuItem("reinitialiser a zero");
	    //MenuItem itep7=new MenuItem("stopper ou reprendre");
	    MenuItem itep8=new MenuItem("preparer un etat initial immobile");
	    Menu menu_stationnaire=new Menu("preparer une onde stationnaire");
	    MenuItem itep10=new MenuItem("mettre ou oter un amortissement critique au bout");
	    MenuItem itep11=new MenuItem("ajouter une osc. forcee sinusoidale ");
	    MenuItem itepr11=new MenuItem("Onde incidente forcee sinusoidale.");	    
	    MenuItem itep12=new MenuItem("ajouter un frottement");
	    MenuItem itep13=new MenuItem("multiplier la frequence par 2");
	    MenuItem itep14=new MenuItem("diviser la frequence par 2");
	    MenuItem itep15=new MenuItem("doubler la masse des elements de la seconde moitie de la chaine");
	    MenuItem itep16=new MenuItem("diviser par 2 la masse des elements de la seconde moitie de la chaine");
	    
	    if(i_demarre<9&&i_demarre!=-1){
		MenuItem mul_nb_elem=new MenuItem("Multiplier par 2 le nb d'elements, max a 200");
		mul_nb_elem.addActionListener(this);
		modifs.add(mul_nb_elem);
		MenuItem div_nb_elem=new MenuItem("Diviser par 2 le nb d'elements, min a 2");
		div_nb_elem.addActionListener(this);
		modifs.add(div_nb_elem);
	    }	
		MenuItem mul_masse=new MenuItem("Multiplier par 2 la masse des atomes");
		mul_masse.addActionListener(this);
		modifs.add(mul_masse);
		MenuItem div_masse=new MenuItem("Diviser par 2 la masse des atomes");
		div_masse.addActionListener(this);
		modifs.add(div_masse);
		MenuItem mul_dist_atomes=new MenuItem("Multiplier par 2 la distance entre atomes");
		mul_dist_atomes.addActionListener(this);
		modifs.add(mul_dist_atomes);
		MenuItem div_dist_atomes=new MenuItem("Diviser par 2 la distance entre atomes");
		div_dist_atomes.addActionListener(this);
		modifs.add(div_dist_atomes);

	    if((i_demarre>=9)||(i_demarre==-1)){
		modifs.add(itep1);
		itep1.addActionListener(this);
		modifs.add(itep2);
		itep2.addActionListener(this);
		//modifs.add(itep3);
		//itep3.addActionListener(this);
	    }
	    if(i_demarre==7||i_demarre==-1){
		modifs.add(itep4);
		itep4.addActionListener(this);
	    }
	    modifs.add(itep6);
	    itep6.addActionListener(this);
	    //modifs.add(itep7);
	    //itep7.addActionListener(this);
	    if(((i_demarre==7)||(i_demarre==8))&&(numero_ensemble==0)){
		modifs.add(itep8);
		itep8.addActionListener(this);
	    }
	    if(i_demarre>=5&&i_demarre<=6||i_demarre>=9){
		if(el[0].fixe==el[nb_el_ens-1].fixe){
		    Menu menu_stat_fermee=new Menu("demies longueurs d onde");
		    for(int ik=1;ik<=nb_el_ens-1;ik++  ){
			if(ik<30){
			    menu_stat_fermee.add(nb_demies_longueurs_d_onde[ik]);
			    nb_demies_longueurs_d_onde[ik].addActionListener(this);
			}
		    }
		    menu_stationnaire.add(menu_stat_fermee);
		}else{
		    Menu menu_stat_ouverte=new Menu("quarts de longueur d onde");
		    for(int ik=1;ik<=2*nb_el_ens-3;ik+=2 ){
			if(ik<60){
			    menu_stat_ouverte.add(nb_quarts_longueurs_d_onde[ik]);
			    nb_quarts_longueurs_d_onde[ik].addActionListener(this);
			}
		    }
		    menu_stationnaire.add(menu_stat_ouverte);
		}
		modifs.add(menu_stationnaire);   
	    }
	    if(i_demarre==1){
		modifs.add(itepr11);
		itepr11.addActionListener(this);
		//itep10.disable();
	    }
	    if(i_demarre<=4){
		modifs.add(itep10);
		itep10.addActionListener(this);
		//itep10.disable();
	    }
	    if(i_demarre==3){
		MenuItem itep_3_zones=new MenuItem("Une zone lourde entre deux zones legeres");
		modifs.add(itep_3_zones);
		itep_3_zones.addActionListener(this);
	    }
	    if(i_demarre<2||i_demarre>8){
		modifs.add(itep11);
		itep11.addActionListener(this);
	    }
	    modifs.add(itep12);
	    itep12.addActionListener(this);
	    if(i_demarre==4||i_demarre==5||i_demarre<=0){
		modifs.add(itep13);
		itep13.addActionListener(this);
		modifs.add(itep14);
		itep14.addActionListener(this);
	    }
	    if(i_demarre==4){
		modifs.add(itep15);
		itep15.addActionListener(this);
		modifs.add(itep16);
		itep16.addActionListener(this);
	    }
	    mb1[numero_ensemble].add(modifs);
	    if(i_demarre>=4&&i_demarre<=6){
		Menu graphes=new Menu("graphes");
		MenuItem itep5=new MenuItem("faire le graphe d'un element");
		MenuItem itep55=new MenuItem("faire le graphe de l'energie");
		graphes.add(itep5);
		itep5.addActionListener(this);
		graphes.add(itep55);
		itep55.addActionListener(this);
		mb1[numero_ensemble].add(graphes);
	    }
	    if(demonst){
		mul_dist_atomes.setEnabled(false);
		div_dist_atomes.setEnabled(false);
		mul_masse.setEnabled(false);
		div_masse.setEnabled(false);
		itep1.setEnabled(false);
		itep2.setEnabled(false);
		//itep3.setEnabled(false);
		itep4.setEnabled(false);
		itep6.setEnabled(false);
		//itep7.setEnabled(false);
		itep8.setEnabled(false);
		menu_stationnaire.setEnabled(false);
		itep10.setEnabled(false);
		itep11.setEnabled(false);
		itep12.setEnabled(false);
		itep13.setEnabled(false);
		itep14.setEnabled(false);
	    }
	    
	    Menu sortir=new Menu("Reprendre ou sortir");
	    MenuItem iteb1=new MenuItem("Revenir a la page principale");
	    sortir.add(iteb1);
	    iteb1.addActionListener(this);
	    MenuItem itebb1=new MenuItem("Revenir a la page initiale avec infos");
	    sortir.add(itebb1);
	    itebb1.addActionListener(this);
	    MenuItem iteb0=new MenuItem("Recommencer");
	    sortir.add(iteb0);
	    iteb0.addActionListener(this);
	    MenuItem itebr0=new MenuItem("Reinitialiser dans les conditions actuelles");
	    sortir.add(itebr0);
	    itebr0.addActionListener(this);
	    MenuItem iteb2=new MenuItem("Desactiver cette fenetre");
	    MenuItem iteb3=new MenuItem("Desactiver les deux fenetres");
	    if(desactiver)
		iteb3=new MenuItem("Reactiver les deux fenetres");
	    if(desactiver)
		iteb2=new MenuItem("Reactiver cette fenetre");
	    sortir.add(iteb2);
	    iteb2.addActionListener(this);
	    if(!(i_demarre==2||i_demarre==7)){
		sortir.add(iteb3);
		iteb3.addActionListener(this);
	    }
	    if(demonst){
		iteb0.setEnabled(false);
		itebr0.setEnabled(false);
		iteb1.setEnabled(false);
		//itebb1.setEnabled(false);
		iteb2.setEnabled(false);
		iteb3.setEnabled(false);
	    }
	    
	    mb1[numero_ensemble].add(sortir);
	    if(i_demarre>=0){
		Menu aide=new Menu("aide et commentaires");
		MenuItem itab1=new MenuItem("aide et commentaires");
		aide.add(itab1);
		itab1.addActionListener(this);
		mb1[numero_ensemble].add(aide);
	    }
	    setMenuBar(mb1[numero_ensemble]);
	}
    }
    public void run(){
	subject.occupied=true;
	//System.out.println("entree run "+" numero_ensemble "+numero_ensemble+" subject.ensemble[1-numero_ensemble].desactiver "+ subject.ensemble[1-numero_ensemble].desactiver);
	if(i_demarre!=5&&i_demarre!=6&&i_demarre!=7)
	    comment="Vous pouvez deplacer un atome avec la souris";
	vas_y_run=true;
	if(pas_a_pas&&nb_de_vrais_pas==0){
	    vas_y_run=false;
	    if(cliquee)
		nb_de_vrais_pas++;
	    cliquee=false;
	    if(!((i_demarre==2||i_demarre==7)&&numero_ensemble==1))
		comment="Pour le prochain pas, cliquez dans cette fenetre";
	    else
		comment="";
	}
	if(i_demarre==7&&numero_ensemble==1)
	    vas_y_run=subject.ensemble[0].vas_y_run;
	cliquee=false;
	//subject.gr.setColor(Color.red);
	//subject.gr.drawString("*numero_ensemble "+numero_ensemble,450,150+20*numero_ensemble);
	if(vas_y_run){
	    nb_de_vrais_pas++;
	    if(nb_de_vrais_pas==5)
		nb_de_vrais_pas=0;
	    if(oscillations_forcees_sinusoidales||ajouter_osc){
		if(numero_ensemble==0){
		    n_passages++;
		    if(n_passages>10000)
			n_passages=10000;
		}
		if((!ne_plus_calc_0)&&(!stopper)){
		    phase+=2*pi*dtt*frequence;
		    if(i_demarre<=0||i_demarre==4)
			if(numero_ensemble==0)
			    phase+=2*pi*dtt*frequence*4;		    
		    if(i_demarre!=2)
			ampl=Amplitude_maximale*Math.sin(phase);
		    else if(numero_ensemble==0){
			ampl=Amplitude_maximale*Math.sin(phase);
			if(retardons){
			    if(n_passages>npassages_retard){
				phase_bis+=2*pi*dtt*frequence;	    
				ampl_bis=Amplitude_maximale*Math.sin(phase_bis);
			    }else
				ampl_bis=0.;
			}else if (dephasons){
			    phase_bis=phase+dephasage;	    
			    ampl_bis=Amplitude_maximale*Math.sin(phase_bis);
			}else
			    ampl_bis=ampl;
			//System.out.println(" ampl "+(float)ampl+" ampl_bis "+(float)ampl_bis+" n_passages "+n_passages);
		    }
		    if(i_demarre==3)
			ampl*=2;
		    /*
		      if(ajouter_osc)ampl/=1.5;
		    */
		    //if(ajouter_osc)
		    //ampl/=4;
		    /*
		      if(i_demarre==0){
		      zoom_y=3;facy=4.;
		      }
		    */
		    //if(numero_ensemble==0)System.out.println("ampl "+ampl+" phase "+phase);
		    if(oscillations_transverses){
			//System.out.println("ajouter_osc "+ajouter_osc+" ampl "+ampl );
			el[0].yreel=ampl*dimensionpixel;
			if(i_demarre==2){
			    if(numero_ensemble==0)
				el[0].yreel=(ampl+ampl_bis)*dimensionpixel;
			    else if(numero_ensemble==1)
				el[nb_el_ens].yreel=ampl_bis*dimensionpixel;
			    //System.out.println("&&&&numero_ensemble "+numero_ensemble+ " ampl "+(float)ampl+" ampl_bis "+(float)ampl_bis+" n_passages "+n_passages);
			    //if(n_passages==110)
			    //el[1000]=null;
			}
			if((i_demarre<=0||i_demarre==1&&!oscillations_forcees_sinusoidales||ajouter_osc&&!oscillations_forcees_sinusoidales)&&(el[0].yreel<0)){
			    el[0].yreel=0.0;ne_plus_calc_0=true;ajouter_osc=false;
			    el[0].yc_element=yrepos+(int) Math.round((el[0].yreel+el[0].yreel0)/dimensionpixel);
			}
		    }else{
			el[0].xreel=ampl/2.*dimensionpixel;
			if((i_demarre<=1)&&(el[0].xreel<0)){
			    el[0].xreel=0.0;ne_plus_calc_0=true;ajouter_osc=false;
			    el[0].xc_element=xrepos+(int) Math.round((el[0].xreel+el[0].xreel0)/dimensionpixel);
			}
		    }
		}
	    }
	    //subject.gr.setColor(Color.red);
	    //subject.gr.drawString("$$$$$numero_ensemble "+numero_ensemble,450,190+20*numero_ensemble);

	    //System.out.println("calcule des forces dans run");
	    for(int ijk=0;ijk<4;ijk++){
		//subject.gr.drawString("$$$$$ijk "+ijk+"nb_el_ens "+nb_el_ens+" "+subject.second_ensemble_virtuel,450,230+20*numero_ensemble);
		calculforces_et_deplace(0,nb_el_ens);

		if(subject.second_ensemble_virtuel&&numero_ensemble==1){
		    calculforces_et_deplace(nb_el_ens,2*nb_el_ens);
		    if(i_demarre==7)
			calculforces_et_deplace(2*nb_el_ens,3*nb_el_ens);
		    //System.out.println("el[10].vity "+el[10].vity+"el[30].vity "+el[30].vity+"el[50].vity "+el[50].vity);
		    
		}
	    }
	    if(i_demarre<=0&&n_passages<10)
		for(int iq=1;iq<20;iq++)
		    if(el[iq].yreel<0.0){
			el[iq].yreel=0.0;
			el[iq].yc_element=yrepos+(int) Math.round((el[iq].yreel+el[iq].yreel0)/dimensionpixel);
			
		    }
	}
	//subject.gr.setColor(Color.red);
	//subject.gr.drawString("££££££numero_ensemble "+numero_ensemble,450,270+20*numero_ensemble);

	cliquee=false;
	subject.occupied=false;
    }
    public void cree_element(int k, double masse_ch){
	if(el[k]!=null)
	    el[k]=null;
	if(i_demarre!=3||k<nb_el_ens/2)
	    el[k]=new element(k,masse_ch);
	else{
	    if(numero_ensemble==0)
		el[k]=new element(k,2*masse_ch);
	    else
		el[k]=new element(k,8*masse_ch);
	}
    }
    public void actionPerformed(ActionEvent e){
	commande_a_executer=e.getActionCommand();
	System.out.println("numero_ensemble "+numero_ensemble+" n_stoppages "+n_stoppages+" commande_a_executer "+commande_a_executer);
	//if(command!="")n_stoppages=0;
	if(commande_a_executer!=""){
	    Date maintenant=new Date();
	    subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	}
    }
    public void traite_commande(){
	int i, iq, jq, iqmin, ii;
	double xaa, dxmin, pas;
	System.out.println("*numero_ensemble "+numero_ensemble+" command "+command);
	while(subject.occupied){
	    System.out.println("*numero_ensemble "+numero_ensemble+" command "+command);
	}
	iqmin=0;n_demi=2; n_quart=3;
	if(!(i_demarre==5||i_demarre==6))
	    onde_stat=false;
	if(command!="")fin_gere_menus_souris=false;
	if (command=="aide et commentaires"){
	    subject.appelle_comm(command,i_demarre);
	}
	if ((command=="Revenir a la page principale")||(command=="Recommencer"||command=="Revenir a la page initiale avec infos")){
	    le_virer=true;
	    //System.out.println("toto ");
	}
	if (command=="Reinitialiser dans les conditions actuelles"){
	    command="";
	    recommencer();
	}
	if (command=="Desactiver cette fenetre"||command=="Reactiver cette fenetre"){
	    desactiver=!desactiver;
	    if(i_demarre==2||i_demarre==7)
		subject.ensemble[1-numero_ensemble].desactiver=desactiver;
	}
	if (command=="Desactiver les deux fenetres"||command=="Reactiver les deux fenetres"){
	    desactiver=!desactiver;
	    subject.ensemble[1-numero_ensemble].desactiver=desactiver;
	}
	if (command=="sur x, facteur 2"){
	    zoom_x++;
	    facx=determine_zoom(zoom_x);
	}
	if (command=="sur x, facteur 0.5"){
	    zoom_x--;
	    facx=determine_zoom(zoom_x);
	}
	if (command=="sur y, facteur 2"){
	    zoom_y++;
	    System.out.println("commande de zoom recue numero_ensemble "+numero_ensemble+" zoom_y "+zoom_y);
	    facy=determine_zoom(zoom_y);
	}
	if (command=="sur y, facteur 0.5"){
	    zoom_y--;
	    facy=determine_zoom(zoom_y);
	}
	if (command=="translation vers la droite"){
	    vasadroite=vasadroite+100;
	    command="";
	}
	if (command=="translation vers la gauche"){
	    vasadroite=vasadroite-100;
	    command="";
	}
	if (command=="pas_a_pas"||command=="pas pas_a_pas"){
	    pas_a_pas=!pas_a_pas;
	    barre_des_menus();
	    if(subject.n_ensembles==2)
		subject.ensemble[1-numero_ensemble].pas_a_pas=pas_a_pas;
	    if(i_demarre==7||i_demarre==8)
		oscillations_forcees_sinusoidales=false;
	}
	if(command=="Multiplier par 2 le nb d'elements, max a 200"){
	    if(subject.nb_elem[numero_ensemble]<100){
		subject.nb_elem[numero_ensemble]*=2;
		command="Recommencer";
		le_virer=true;
	    }
	}
	if(command=="Diviser par 2 le nb d'elements, min a 2"){
	    if(subject.nb_elem[numero_ensemble]>3){
		subject.nb_elem[numero_ensemble]/=2;
		command="Recommencer";
		le_virer=true;
	    }
	}
	if(command=="Multiplier par 2 la masse des atomes"){
	    if(i_demarre==2||i_demarre==7)
		subject.masse_chaine[1]*=2;
	    subject.masse_chaine[numero_ensemble]*=2;
	    command="Recommencer";
	    le_virer=true;
	}
	if(command=="Diviser par 2 la masse des atomes"){
	    if(i_demarre==2||i_demarre==7)
		subject.masse_chaine[1]/=2;
	    subject.masse_chaine[numero_ensemble]/=2;
	    command="Recommencer";
	    le_virer=true;
	}
	if(command=="Multiplier par 2 la distance entre atomes"){
	    if(i_demarre==2||i_demarre==7)
		subject.pas_chaine[1]*=2;
	    subject.pas_chaine[numero_ensemble]*=2;
	    command="Recommencer";
	    le_virer=true;
	}
	if(command=="Diviser par 2 la distance entre atomes"){
	    if(i_demarre==2||i_demarre==7)
		subject.pas_chaine[1]/=2;
	    subject.pas_chaine[numero_ensemble]/=2;
	    command="Recommencer";
	    le_virer=true;
	}
	if (command=="oscillations transverses "){
	    oscillations_transverses=true;command="";
	    //enableitem(hmenu_modifs, tirez_sur_la_chaine);oscil=true;
	}
	if (command=="oscillations longitudinales"){
	    oscillations_transverses=false;command="";
	    //disableitem(hmenu_modifs, tirez_sur_la_chaine);;oscil=true;
	}
	if (command=="oscillations forcees"){
	    subject.temps_minimum=2400;
	    oscillations_forcees_sinusoidales=true;command="";
	    //disableitem(hmenu_modifs, tirez_sur_la_chaine);;oscil=true;
	}
	if (i_demarre==3&&command=="Une zone lourde entre deux zones legeres"){
	    trois_zones=true;
	    subject.ensemble[1-numero_ensemble].trois_zones=true;;
	    for (int ij=0;ij<nb_el_ens/3;ij++){
		el[ij].masse=masse_atomes*1e-3/(6e23);
		subject.ensemble[1-numero_ensemble].el[ij].masse=subject.ensemble[1-numero_ensemble].masse_atomes*1e-3/(6e23);
	    }
	    for (int ij=nb_el_ens/3;ij<2*nb_el_ens/3;ij++){
		el[ij].masse=2*masse_atomes*1e-3/(6e23);
		subject.ensemble[1-numero_ensemble].el[ij].masse=8*subject.ensemble[1-numero_ensemble].masse_atomes*1e-3/(6e23);
	    }
	    for (int ij=2*nb_el_ens/3;ij<nb_el_ens;ij++){
		el[ij].masse=masse_atomes*1e-3/6e23;
		subject.ensemble[1-numero_ensemble].el[ij].masse=subject.ensemble[1-numero_ensemble].masse_atomes*1e-3/6e23;
	    }
	    command="";
	    init_cond(true,true);
	    subject.ensemble[1-numero_ensemble].init_cond(true,true);
	    ne_plus_calc_0=false;
	    ajouter_osc=true;
	    n_passages=0;
	    phase=0.;
	    subject.ensemble[1-numero_ensemble].ne_plus_calc_0=false;
	    subject.ensemble[1-numero_ensemble].ajouter_osc=true;
	    subject.ensemble[1-numero_ensemble].phase=0.;
	}
	if (command=="ajouter une osc. forcee sinusoidale "){
	    command="";
	    ajouter_osc=!ajouter_osc;//oscillations_forcees_sinusoidales=false;
	    fixer_cdm=false;if(ajouter_osc)ne_plus_calc_0=false;
	    frequence=frequence0;phase=0.0;
	    //	    if (frequence>frequence_min)
	    //dtt*=frequence0/frequence;
	    System.out.println("dtt "+dtt+"frequence"+frequence+" ajouter_osc "+ajouter_osc+" oscillations_forcees_sinusoidales "+oscillations_forcees_sinusoidales);
	}
	boolean modifier=false;
	if (command=="changer de ref. cdm/labo pour le dessin"){
	    command="";
	    modifier=true;
	    draw_cdm=!draw_cdm;
	    init_cond(true,true);
	    if (draw_cdm)
		comment="Les dessins sont dans le referentiel du cdm avec les conditions initiales ";
	    else
		comment="Les dessins sont dans le referentiel du labo avec les conditions initiales";
	}
	if(i_demarre==2){
	    System.out.println("****numero_ensemble "+numero_ensemble+" command "+command);
	    for (int j=0;j<5;j++){
		if(command==menu_deph_[j]||command==menu_ret_[j]){
		    System.out.println(" trouvé dephase ");
		    if(command==menu_deph_[j]){
			dephasons=true;
			retardons=false;
			dephasage=j*pi/2;
		    }else{
			retardons=true;
			dephasons=false;
			phase_bis=0.;
			npassages_retard=j*50;
		    }
		    n_passages=0;
		    command="";
		    phase=0.;
		    init_cond(true,true);
		    break;
		}
	    }
	}
	if(i_demarre==5||i_demarre==6||i_demarre==9||i_demarre==10){
	    System.out.println("****numero_ensemble "+numero_ensemble+" command "+command);
	    if(el[0].fixe==el[nb_el_ens-1].fixe){
		int n_maxi=nb_el_ens-1;
		if(n_maxi>59)
		    n_maxi=59;
		for(int ik=1;ik<=n_maxi;ik++){
		    System.out.println(nb_demies_longueurs_d_onde_[ik]);
		    if(command==nb_demies_longueurs_d_onde_[ik]){
			System.out.println(" trouvé ");
			command="";
			onde_stat=true;
			comment_init=""+ik+" demies longueurs d'onde";
			subject.ensemble[1-numero_ensemble].command="";
			n_demi=ik;
			lambda=2*(nb_el_ens -1)* distance_inter_atomes/n_demi;
			oscillations_forcees_sinusoidales=false;
			command="";
			cliquee=false;
			demarre_onde_stationnaire();
			break;
		    }
		}
	    }else{
		int n_maxi=2*nb_el_ens-3;
		if(n_maxi>59)
		    n_maxi=59;
		for(int jk=1;jk<=n_maxi;jk+=2){
		    System.out.println(nb_quarts_longueurs_d_onde_[jk]);
		    if(command==nb_quarts_longueurs_d_onde_[jk]){
			System.out.println(" trouvé ");
			command="";
			onde_stat=true;
			subject.ensemble[1-numero_ensemble].command="";
			cliquee=false;
			n_quart=jk;
			comment_init=""+jk+" quarts de longueur d'onde";
			lambda=4*(nb_el_ens-0.5)* distance_inter_atomes/n_quart;
			oscillations_forcees_sinusoidales=false;
			demarre_onde_stationnaire();
			break;
		    }
		}
	    }
	}
	if (command=="fixer_le_cdm")
	    {command="";
	    modifier=true;
	    fixer_cdm=!fixer_cdm;
	    if(atomefixe)
		comment="Il n'y a plus d' atome fixe et le cdm est fixe.";
	    init_cond(true,true);
	    atomefixe=false;
	    }
	if (command=="reinitialiser a zero")
	    {command="";
	    modifier=true;
	    //	revenir_conditions_initiales=true;
	    comment="Les conditions initiales sont reprises";
	    init_cond(true,true);
	    }
	if (command=="multiplier la frequence par 2"){
	    command="";
	    frequence*=2.;
	    frequence_min*=2.;
	    frequence_max*=2.;
	    frequence0*=2.;
	    frequence_centrale*=2.;
	    modifier=true;ne_plus_calc_0=false;
	    //	revenir_conditions_initiales=true;
	    comment="Les conditions initiales sont reprises";
	    init_cond(true,true);
	    }
	if (command=="doubler la masse des elements de la seconde moitie de la chaine"){
	    command="";
	    for (jq=nb_el_ens/2;jq< nb_el_ens-1;jq++)
		el[jq].masse*=2;	
	    modifier=true;	
	    init_cond(true,true);
	    ne_plus_calc_0=false;
	}
	if (command=="diviser par 2 la masse des elements de la seconde moitie de la chaine"){
	    command="";
	    for (jq=nb_el_ens/2;jq< nb_el_ens-1;jq++)
		el[jq].masse/=2;
	    modifier=true;	
	    init_cond(true,true);
	    oscillations_forcees_sinusoidales=true;
	    ne_plus_calc_0=false;
	}
	if (command=="diviser la frequence par 2"){
	    command="";
	    frequence/=2.;
	    frequence_min/=2.;
	    frequence_max/=2.;
	    frequence0/=2.;
	    frequence_centrale/=2.;
	    modifier=true;ne_plus_calc_0=false;
	    comment="Les conditions initiales sont reprises";
	    init_cond(true,true);
	    }
	if (command=="stopper ou reprendre"){
		command="";	
		int i_c=1;if(numero_ensemble==1)i_c=0;
		modifier=true;
		System.out.println(" stopper "+stopper+" numero_ensemble "+numero_ensemble+" n_stoppages "+n_stoppages );
		if(n_stoppages==0){
		    System.out.println(" toto " );
		    stopper=!stopper;
		    n_stoppages++;
		    if(stopper){
			comment="Les vitesses des elements sont annulees,les elements repartent avec une vitesse nulle.";
			for(jq=0;jq<nb_el_ens;jq++){
			    el[jq].vitx_prec=el[jq].vitx;
			    el[jq].vity_prec=el[jq].vity;
				el[jq].vitx=0;
				el[jq].vity=0;
			}
		    }else{
			comment="Si vous voulez redemarrer, rechoisissez la meme operation";
			for(jq=0;jq<nb_el_ens;jq++){
			    el[jq].vitx=el[jq].vitx_prec;
			    el[jq].vity=el[jq].vity_prec;
			}
		    }
		    System.out.println(" toto1 " );
		    System.out.println("numero_ensemble"+numero_ensemble+" n_stoppages "+n_stoppages);
		    if(i_c<subject.n_ensembles){
			subject.ensemble[i_c].commande_a_executer="stopper ou reprendre";
		    }
		}
		if((stopper==subject.ensemble[i_c].stopper)&&(n_stoppages==1)&&(subject.ensemble[i_c].n_stoppages==1)){
			System.out.println("toto2 " );
			command="";
			n_stoppages=0;
			subject.ensemble[i_c].command="";
			subject.ensemble[i_c].n_stoppages=0;
		    }
	    }
	if (command=="mettre ou oter un amortissement critique au bout"){
	    command="";modifier=true;
	    amortissement_critique=!amortissement_critique;
	    if(amortissement_critique)
			comment="l'ensemble est amorti en bout, voulez vous le  desamortir?(o/n)";
	    else
			comment="l'ensemble n'est pas amorti en bout, voulez vous l'amortir?(o/n)";
	    }
	if (command=="Onde incidente forcee sinusoidale."){
	    command="";modifier=true;
	    oscillations_forcees_sinusoidales=!oscillations_forcees_sinusoidales;
	    oscillations_forcees_sinusoidales_dernier=oscillations_forcees_sinusoidales;
	    subject.ensemble[1-numero_ensemble].oscillations_forcees_sinusoidales=oscillations_forcees_sinusoidales;
	    subject.ensemble[1-numero_ensemble].oscillations_forcees_sinusoidales_dernier=oscillations_forcees_sinusoidales;
	    recommencer();
	}
	if (command=="fixer ou liberer un element"){
	    fixer_element=!fixer_element;
	    modifier=true;	stopper=true;
	    comment="Cliquez sur l'atome que vous voulez fixer ou rendre libre et dans le coin en haut a gauche pour finir";
	}
	if (command=="faire ou abandonner le graphe d'un element"){
	    modifier=true;
	    if(graphe_on){
		courbe.dispose();graphe_on=false;achoisi=-1;graphe=false;
		command="";	
	    }
	}
	if (command=="faire le graphe de l'energie"){
	    modifier=true;
	    System.out.println("graphe "+graphe+" graphe_on "+graphe_on+" achoisi "+achoisi);
	    if(fen_energie==null){
		fen_energie=new graphique("Graphe de l'energie dans les ressorts et les masses libres",false,0);
		command="";	
	    }
	    else if(fen_energie!=null){
		fen_energie.dispose();
		command="";	
	    }
	}
	if (command=="ajouter un frottement"){
	    frotter=!frotter;ffrr=(fmax2+fmin2)/2.0;
	    command="";	
	    command="";	
	}
	//	if(modifier)return false;
	//return dg;
	//	return true;
    }
    void recommencer(){
	init_cond(true,true);
	subject.ensemble[1-numero_ensemble].init_cond(true,true);
	ne_plus_calc_0=ne_plus_calc_dernier;
	ajouter_osc=ajouter_osc_dernier;
	oscillations_forcees_sinusoidales=oscillations_forcees_sinusoidales_dernier;
	n_passages=0;
	phase=0.;
	subject.ensemble[1-numero_ensemble].ne_plus_calc_0=subject.ensemble[1-numero_ensemble].ne_plus_calc_dernier;
	subject.ensemble[1-numero_ensemble].ajouter_osc=subject.ensemble[1-numero_ensemble].ajouter_osc_dernier;
	subject.ensemble[1-numero_ensemble].phase=0.;
	subject.ensemble[1-numero_ensemble].oscillations_forcees_sinusoidales=subject.ensemble[1-numero_ensemble].oscillations_forcees_sinusoidales_dernier;
    }
    double determine_zoom(int zoom){
	double fac=1.0;int i=0;
	if (zoom>1)
	    for(i=2;i<=zoom;i++)
		fac*=2;
	if (zoom<1)
	    for(i=zoom;i<1;i++)
		fac/=2.0;
	return fac;
    }
    public double energie_totale(){
	double a=0.;double b=0.;
	for(int i=0;i<nb_el_ens;i++){
	    if(!el[i].fixe)
		a+=0.5*el[i].masse*(Math.pow(el[i].vitx,2)+Math.pow(el[i].vity,2));
	    if (oscillations_transverses){
		if(i+1<nb_el_ens)
		    b+=0.5*raideur*Math.pow(el[i].yreel-el[i+1].yreel,2);
		if(i-1>=0)
		    b+=0.5*raideur*Math.pow(el[i].yreel-el[i-1].yreel,2);
	    }else{
		if(i+1<nb_el_ens)
		    b+=0.5*raideur*Math.pow(el[i].xreel-el[i+1].xreel,2);
		if(i-1>=0)
		    b+=0.5*raideur*Math.pow(el[i].xreel-el[i-1].xreel,2);
	    }
	}
	double c=a+b;
	//	System.out.println("a "+a+" b "+b+" c "+c);
	//System.out.println(" el[5].yreel "+el[5].yreel+" el[5].vity "+el[5].vity);
	return (c);
    }
    public void init_cond(boolean coordonnees,boolean forces_vitesses){
	System.out.println("ùùùùùùùùùùùùùùùùùùùùùùnb_el_ens "+nb_el_ens+" numero_ensemble "+numero_ensemble);
	zezer(el,nb_el_ens,coordonnees,forces_vitesses);
	if(subject.second_ensemble_virtuel&&numero_ensemble==0)
	    if(subject.ensemble[1]!=null){
		zezer(subject.ensemble[1].el,nb_el_ens,coordonnees,forces_vitesses);
		if(i_demarre==7)
		    zezer(subject.ensemble[1].el,3*subject.ensemble[1].nb_el_ens,coordonnees,forces_vitesses);
		else if(i_demarre==2)
		    zezer(subject.ensemble[1].el,2*subject.ensemble[1].nb_el_ens,coordonnees,forces_vitesses);
	    }
    }
    public void zezer(element[] elem,int nb_elem,boolean coordonnees,boolean forces_vitesses){
	//System.out.println("ùùùùùùùùùùùùùùùùùùùùùùnb_el_ens "+nb_el_ens);
	for(int jq=0;jq<nb_elem;jq++){
	    if(forces_vitesses){
		//System.out.println(" jq "+jq+" nb_elem "+nb_elem);
		elem[jq].vitx=0;
		elem[jq].vity=0;
		elem[jq].vitxp=0;
		elem[jq].vityp=0;
		int i_en=jq/nb_el_ens;
		int jq_min=i_en*nb_el_ens;int jq_max=nb_el_ens+jq_min;
		for (int kq=jq_min;kq< jq_max;kq++){
		    //System.out.println("kq "+kq+" jq_min "+jq_min+" jq_max "+jq_max+" numero_ensemble "+numero_ensemble);	    
		    if (oscillations_transverses)
			elem[kq].fy_element=force_ressort(kq,jq_min,elem);
		    else
			elem[kq].fx_element=force_ressort(kq,jq_min,elem);
		}
	    }
	    if(coordonnees){
		elem[jq].xreel=0.0;
		elem[jq].xreelp=0.0;
		elem[jq].xc_element=xrepos+(int) Math.round(elem[jq].xreel0/dimensionpixel);
		elem[jq].yreel=0.0;
		elem[jq].yreelp=0.0;
		elem[jq].yc_element= yinitial;
		elem[jq].fx_element=0;
		elem[jq].fy_element=0;
	    }	    
	    //System.out.println("$jq "+jq+" elem[jq].fy_element "+elem[jq].fy_element+" elem[jq].vity "+ elem[jq].vity +" elem[jq].yreel "+ elem[jq].yreel+" elem[jq].yreelp "+ elem[jq].yreelp );
	}
	phase=0.0;
    }
    private double force_ressort(int jq,int jq_min,element[] elem){
	double dx,dy,ddx,fx_element,fy_element; 
	//		System.out.println("jq force 1 "+jq);
	fx_element=0;
	fy_element=0;
	//if(jq==0)System.out.println("jq  "+jq+" raideur "+raideur);
	for (int iq=jq-1;iq< jq +2;iq +=2){
	    //System.out.println("iq,jq,jq_min  "+iq+" "+jq+" "+jq_min+" ");
	    if ( !((iq-jq_min)<0) &&  !((iq-jq_min)>nb_el_ens-1 )){
		dx=elem[jq].xreel+elem[jq].xreel0-(elem[iq].xreel+elem[iq].xreel0);
		ddx=elem[jq].xreel-elem[iq].xreel;
		dy=elem[jq].yreel-elem[iq].yreel;
		/*
		if(i_demarre==2&&numero_ensemble==1&&(jq==nb_el_ens||jq==nb_el_ens+1)){
		    System.out.println("iq,jq  "+iq+" "+jq+" "+" dy/dimensionpixel "+(float)dy/dimensionpixel+" elem[jq].yreel "+(float)elem[jq].yreel+" elem[iq].yreel "+(float)elem[iq].yreel);
		    if(jq==nb_el_ens+1)
			elem[1000]=null;
		}
		*/
		if(oscillations_transverses){
		    fy_element=fy_element+dy;
		    //if(jq==0)System.out.println("jq "+jq+"yreel "+elem[jq].yreel+" iq "+iq+" elem[iq].yreel "+elem[iq].yreel+" dy "+dy+ "fy "+fy_element);    
		}else{ 
		    fx_element=fx_element+ddx;
		}
	    }
	}
	if(oscillations_transverses){
	    //if(jq<=5)
	    //System.out.println(" jq "+jq+" fy_element "+fy_element+" elem[jq].masse "+elem[jq].masse);
	    fy_element=-fy_element*raideur;
	    return fy_element;
	}else{
	    fx_element=-fx_element*raideur;
	    return fx_element;
	}
    }
    void calculforces_et_deplace (int jq_min,int jq_max){
	int jq, kq, ii;
	double som, dxg, dxd;
	for (jq=jq_min;jq< jq_max;jq++)
	    if (oscillations_transverses){
		el[jq].vityp=el[jq].vity;el[jq].yreelp=el[jq]. yreel;
	    }else{
		el[jq].vitxp=el[jq].vitx;el[jq].xreelp=el[jq].xreel;
	    }
	for (int i=1;i<=16;i++){
	    for (int j=0;j<=1;j++){	
		for (jq=jq_min;jq< jq_max;jq++){
		    if(! el[jq].deplacer)
			if (oscillations_transverses){
			    el[jq].fy_element=force_ressort(jq,jq_min,el);
			    if(frotter)el[jq].fy_element-=ffrr*el[jq].vity;
			}
			else{
			    el[jq].fx_element=force_ressort(jq,jq_min,el);
			    if(frotter)el[jq].fx_element-=ffrr*el[jq].vitx;
			}
		    if (amortissement_critique&&jq==jq_max-1&&(i_demarre>=1||i_demarre<=3))
			if (oscillations_transverses)
			    el[jq].fy_element=el[jq].fy_element-Math.sqrt(el[jq].masse*raideur)*el[jq].vity;
			else
			    el[jq].fx_element=el[jq].fx_element- Math.sqrt(el[jq].masse* raideur)*el[jq].vitx;
		}
		for (jq=jq_min;jq< jq_max;jq++){
		    //if((numero_ensemble==0)&&(jq==0))System.out.println(" el[jq].deplacer "+el[jq].deplacer+ " el[jq].fixe "+el[jq].fixe+" amortissement_critique "+amortissement_critique);
		    if(!el[jq].deplacer && !el[jq].fixe && ! stopper){
			if (fixer_cdm && (jq==jq_max-1)){
			    som=0;
			    if (oscillations_transverses){
				for (kq=jq_min;kq< jq_max-1;kq++)
				    som=som+el[kq].yreel*el[kq].masse;
				el[jq].yreel=-som/el[jq].masse;
			    }
			    else{
				for (kq=jq_min;kq< jq_max-1;kq++)
				    som=som+el[kq].xreel *el[kq]. masse;
				el[jq].xreel=-som /el[jq]. masse;
			    }
			}else{
			    if (oscillations_transverses){
				el[jq].vity=el[jq].vityp+ (el[jq].fy_element)/el[jq].masse* dtt;
				//if((numero_ensemble==0)&&(jq==0))System.out.println(" oscillations_forcees_sinusoidales "+oscillations_forcees_sinusoidales+ " oscil_composees "+oscil_composees+ "oscil_paquet "+oscil_paquet+" ajouter_osc "+ajouter_osc);
				if (!(jq==0&&(oscillations_forcees_sinusoidales || oscil_composees || oscil_paquet||ajouter_osc))){
				    el[jq].yreel=el[jq].yreelp+0.5* (el[jq].vity+ el[jq].vityp)*dtt;
				    /*
				      if(jq==0&&numero_ensemble==0)
				      System.out.println(" jq "+jq+" el[jq].fy_element "+(float)el[jq].fy_element+" el[jq].vity "+ (float)el[jq].vity +" el[jq].yreel "+ (float)el[jq].yreel+" el[jq].yreelp "+ (float)el[jq].yreelp );
				      //if(jq==59)
				      //el[1000]=null;	
				      }
				    */
				}
			    }else{
				el[jq].vitx=el[jq].vitxp+(el[jq].fx_element)/(el[jq].masse* 1.0)*dtt;
				if (!((jq==0)&&(oscillations_forcees_sinusoidales || oscil_composees || oscil_paquet||ajouter_osc)))
				    el[jq].xreel=el[jq].xreelp+ 0.5*(el[jq].vitx+ el[jq].vitxp)*dtt;
			    }
			}
		    }else{
			el[jq].vitx=0.0;
			el[jq].vity=0.0;
		    }
		}
		for (jq=jq_min;jq<jq_max;jq++)
		    if(j==0){
			if (oscillations_transverses)
			    el[jq].yreel=0.5*(el[jq].yreel+el[jq].yreelp);
			else	
			    el[jq].xreel=0.5*(el[jq].xreel+el[jq].xreelp);	
			//if(jq==10)System.out.println("yreel "+el[jq].yreel+"yreelp "+el[jq].yreelp+"yc_element "+el[jq].yc_element);
		    }else{
			if (oscillations_transverses){
			    el[jq].vityp=el[jq].vity;el[jq].yreelp=el[jq]. yreel;
			}
			else{
			    el[jq].xreelp=el[jq].xreel;el[jq].vitxp=el[jq].vitx;
			}
		    }
		//if(numero_ensemble==0)System.out.println("j "+j+" i "+i+" el[10].fy_element "+el[10].fy_element+" el[10].vity "+ el[10].vity );

	    }
	}
	for (jq=jq_min;jq<jq_max;jq++){
	    if (oscillations_transverses)
		el[jq].yc_element=el[jq].yc_premier_element +(int) Math.round((el[jq].yreel0+el[jq].yreel)/dimensionpixel);
	    else
		el[jq].xc_element=el[jq].xc_premier_element+(int) Math.round((el[jq].xreel0+el[jq].xreel)/dimensionpixel);
	}
    }      
    public boolean gerelescarres(){
	int	i, iq;
	double logfreq;boolean dg;
	dg=false;
	if((i_demarre==4)||(i_demarre==-1)){
	    if((ppmousev>top0)&&(ppmousev<bot0)&&(ppmouseh>left0)&&(ppmouseh<right0)){
		frequence=frequence_centrale*(0.5+(double)(ppmouseh-left0)/(right0-left0));
	    if(fen_energie!=null){
		fen_energie.dispose();	
		fen_energie=null;
		fen_energie=new graphique("Graphe de l'energie dans les ressorts et les masses libres",false,0);
		command="";	
	    }

		dg=true;init_cond(true,true);
		System.out.println("frequence "+ frequence+"frequence_centrale "+ frequence_centrale);
	    }
	}
	if(frotter)
	    if((ppmousev>top2)&&(ppmousev<bot2)&&(ppmouseh>left2)&&(ppmouseh<right2)){
		ffrr=fmin2+(ppmouseh-left2)*(fmax2-fmin2)/(right2-left2);
		System.out.println("Le coefficient de frottement vaut "+ ffrr);
	    }
	//dessin_corde_chaine_tuyaux(false);
	//if (dg)rectangles_run(g);
	return dg;
    }
    void rectangles_run (){
	int ppv;
	if((i_demarre==4)||(i_demarre==-1)){
	    if (oscillations_forcees_sinusoidales || oscil_composees || oscil_paquet){
		grph.setColor(Color.black);
		grph.drawString("Adjustment of frequency ",left0, bot0+10);
		grph.drawString("0.5",left0-15, bot0);
		grph.drawString("1.5",right0+5, bot0);
		subject.paintrect_couleur(grph,top0, left0, bot0, right0, Color.gray);
		ppv=left0+(int)Math.round((right0-left0)*(0.5+(frequence-frequence_centrale)/frequence_centrale));
		subject.paintrect_couleur(grph,top0, left0, bot0, ppv, Color.black);
	    }
	}
	if(frotter){
	    grph.drawString("Friction ",left2+20, bot2+10);
	    grph.drawString("0",left2-15, bot2);
	    grph.drawString("3e-13.v",right2+5, bot2);
	    subject.paintrect_couleur(grph,top2, left2, bot2, right2, Color.gray);
	    //    invertrect(g,top2+1, left2+1, bot2-1, right2-1);
	    ppv=left2+(int)Math.round((right2-left2)*(ffrr-fmin2)/(fmax2-fmin2));
	    subject.paintrect_couleur(grph,top2, left2, bot2, ppv, Color.black);
	}
    }
    class element{
	int num_chaine,xc_element,yc_element,xc_premier_element,yc_premier_element; 
	int num_element;boolean fixe,deplacer; 
	double xreel,yreel,xreel0,yreel0=0,xreel_prec,yreel_prec;
	double xreelp,yreelp,vitx,vity,vitx_prec,vity_prec,vitxp,vityp,fx_element,fy_element,masse;
	
	element(int nm_l,double masse_c ){
	    num_element=nm_l;
	    fixe=false;if(num_element==0)fixe=true;
	    masse=masse_c *1e-3/(6e23);deplacer=false;
	    xc_premier_element=x_init;yc_premier_element=y_init;
	    if(num_element>=nb_el_ens){
		if(i_demarre==2&&numero_ensemble==1&&num_element==nb_el_ens)
		    fixe=true;
		int num_element_soustrait=nm_l-nb_el_ens;
		if(num_element>=2*nb_el_ens)
		    num_element_soustrait=nm_l-2*nb_el_ens;
		xc_element=xc_premier_element+ (int) Math.round(num_element_soustrait*distance_inter_atomes/dimensionpixel);
	    }else
		xc_element=xc_premier_element+ (int) Math.round(num_element*distance_inter_atomes/dimensionpixel);
	    if(num_element/10*10==num_element)
		System.out.println("num_element "+num_element+" distance_inter_atomes "+distance_inter_atomes+" xc_element "+(float)xc_element);
	    //if(num_element==199)
	    //	el[1000]=null;
	    //yc_element=yc_premier_element+iadd_yrepos;
	    vitx=0;vity=0;vitxp=0;vityp=0;
	    xreel=0.0;yreel=0.0;xreelp=0.0;yreelp=0.0;
	    xreel0=(xc_element-xc_premier_element) *dimensionpixel;
	    fx_element=0;fy_element=0;
	}
    }
    public void demarre_onde_stationnaire(){
	init_cond(true,true);
	System.out.println("lambda "+lambda+" distance_inter_atomes "+distance_inter_atomes);
	for(int iq=0;iq<nb_el_ens;iq++){
	    if(!el[0].fixe)
		if(oscillations_transverses){
		    el[iq].vity=0;
		    el[iq].yreel=-Amplitude_maximale*dimensionpixel*Math.cos((2*pi)/lambda*iq*distance_inter_atomes);
		    System.out.println("yreel[iq,ii] "+ el[iq].yreel+" iq "+  iq);
		}
		else{
		    el[iq].vitx=0;
		    el[iq].xreel=Amplitude_maximale/8.*dimensionpixel*Math.cos((2*pi)/lambda*iq*distance_inter_atomes);
		    System.out.println("xreel[iq,ii] "+ el[iq].yreel+" iq "+  iq);
		}
	    else
		if(oscillations_transverses){
		    el[iq].vity=0;
		    el[iq].yreel=-Amplitude_maximale*dimensionpixel*Math.sin((2*pi)/lambda *iq*distance_inter_atomes);
				//System.out.println("iq "+iq+" el[iq].yreel "+el[iq].yreel);  
		}else{
		    el[iq].vitx=0;
		    el[iq].xreel=Amplitude_maximale/4.*dimensionpixel*Math.sin((2*pi)/lambda*iq*distance_inter_atomes);
		}
	}
	stopper=false;command="";comment="";
    }
    public void paint_ensemble(){
	gTampon.drawImage(crop0,0,0,null);
	xxxx=0;yyyy=0;
	//System.out.println(" n_passages "+n_passages);
	//System.out.println(" scillations_forcees_sinusoidales "+oscillations_forcees_sinusoidales+" ajouter_osc "+ajouter_osc);              ;

	//    System.out.println("nb_el_ens "+nb_el_ens);
	if (draw_cdm){
	    for (int iq=0;iq< nb_el_ens;iq++){
		xxxx=el[iq].xreel*el[iq].masse+xxxx;
		yyyy=el[iq].yreel*el[iq].masse+yyyy;
	    }
	    xxxx=xxxx/masse_totale;yyyy=yyyy/masse_totale;
	}
	int nb=nb_el_ens;
	if(subject.second_ensemble_virtuel&&numero_ensemble==1)
	    if(i_demarre==7)
	       nb=3*nb_el_ens;
	    else if(i_demarre==2)
	       nb=2*nb_el_ens;
	for (int iq=0;iq< nb;iq++){
	    //if((iq>=7)&&(iq<=9))System.out.println("iq "+iq+"el[iq].fixe "+el[iq].fixe+"el[iq].deplacer "+el[iq].deplacer);
	    int iiq=iq;
	    if(subject.second_ensemble_virtuel){
		if(iiq>=nb_el_ens)iiq-=nb_el_ens;
		if(iiq>=nb_el_ens)iiq-=nb_el_ens;
		if(numero_ensemble==0)
		    el[iq].yreel=el[iiq].yreel;
		else
		    el[iq].yreel=el[iq].yreel;
	    }
	    xdessin1=xrepos +(int) Math.round(((el[iq].xreel0+el[iq].xreel-xxxx)/dimensionpixel)*facx+vasadroite);
	    ydessin1=yrepos+(int) Math.round(((el[iq].yreel0+el[iq].yreel-yyyy)/dimensionpixel)*facy);
	    //boolean blue=false;
	    if(i_demarre==2&&numero_ensemble==1&&iq>=nb_el_ens)
		ydessin1+=iadd_yrepos;
	    if(!(i_demarre==3&&trois_zones))
		if(el[iq].fixe)
		    gTampon.drawImage(crop_part_black,xdessin1-ray,ydessin1-ray,null);  
		else if(iq<nb_el_ens/2||el[1].masse==el[nb_el_ens/2].masse)
		    gTampon.drawImage(crop_part_red,xdessin1-ray,ydessin1-ray,null);
		else
		    gTampon.drawImage(crop_part_blue,xdessin1-3,ydessin1-3,null);
	    else
		if(el[iq].fixe)
		    gTampon.drawImage(crop_part_black,xdessin1-ray,ydessin1-ray,null);
		else if(iq<nb_el_ens/3||iq>=2*nb_el_ens/3)
		    gTampon.drawImage(crop_part_red,xdessin1-ray,ydessin1-ray,null);
		else		   
		    gTampon.drawImage(crop_part_blue,xdessin1-ray,ydessin1-ray,null);
 	    if ((iq==nb_el_ens-1)&&amortissement_critique||i_demarre==2&&iq==nb-1)
		subject.paintrect(gTampon,ydessin1-7, xdessin1-7, ydessin1+7, xdessin1+7);
	    if(!(el[iq].fixe||i_demarre==2)){
		if (oscillations_transverses&&subject!=null){
			y_fleche=(int)Math.round(ydessin1+facteur_fleche[i_demarre+1]*el[iq].fy_element/f_norme);
		    jadd_fleche=5;
		    //System.out.println(" iq "+iq+" numero_ensemble "+numero_ensemble+" g "+g+" subject "+subject);
		    subject.drawline_couleur(gTampon,xdessin1+4, ydessin1, xdessin1+4, y_fleche, Color.green);
		    if (y_fleche>ydessin1)
			add_fleche=-jadd_fleche;
		    else
			add_fleche=jadd_fleche;
		    subject.drawline_couleur(gTampon,xdessin1+4+jadd_fleche, y_fleche+add_fleche, xdessin1+4, y_fleche,Color.green);
		    subject.drawline_couleur(gTampon,xdessin1+4-jadd_fleche, y_fleche+add_fleche, xdessin1+4, y_fleche,Color.green);
		}else{
		    int x_fin=(int)Math.round(xdessin1+4+facx*4.*el[iq].fx_element/f_norme);
		    subject.drawline_couleur(gTampon,xdessin1+4, ydessin1,x_fin, ydessin1, Color.green);
		    jadd_fleche=5;
		    if (x_fin>xdessin1)
			add_fleche=-jadd_fleche;
		    else
			add_fleche=jadd_fleche;
		    subject.drawline_couleur(gTampon,x_fin+add_fleche,ydessin1+jadd_fleche, x_fin, ydessin1, Color.green);
		    subject.drawline_couleur(gTampon,x_fin+add_fleche,ydessin1-jadd_fleche, x_fin, ydessin1, Color.green);
		}	
	    }
	}
	gTampon.setColor(Color.blue);
	if(i_demarre!=-1){
	    if (onde_stat)
		gTampon.drawString(comment_init,20,bottom_chaine-top_chaine-28);
	    gTampon.drawString(comment,20,bottom_chaine-top_chaine-42);
	}
	grph.drawImage(crop,0,0,null);
	if (graphe_on)
	    courbe.dessine_graphe();
	if(fen_energie!=null)
	    fen_energie.dessine_graphe();
	if(subject.comm!=null)
	    subject.comm.ecrit_aide();
	//	System.out.println("avant rect ");
	rectangles_run();
	deja_paint=true;
    }
    
    public boolean gere_menus_souris (){
	int i, iq, jq, iqmin, n_demi, n_quart, ii;
	double xaa, dxmin=1000.0, lambda, pas;
	boolean dg;
	iqmin=0;n_demi=2; n_quart=3;//dg=false;
	if(command!="")fin_gere_menus_souris=false;
	//      comment="command"+command;
	boolean modifier=false;
	if (fixer_element){
	    modifier=true;
	    int yi=0;int xi=0;
	    System.out.println("pressee "+pressee);
	    if(pressee){
		xi=ppmouseh;yi=ppmousev;
		for(iq=0;iq<nb_el_ens;iq++){
		    double ddx=Math.abs(xi-vasadroite-((el[iq].xc_element-xrepos)*facx+xrepos));
		    double ddy=Math.abs(yi-((el[iq].yc_element-yrepos)*facy+yrepos));
		    
		    //System.out.println("iq "+iq+" ddx "+ddx+"ddy "+ddy+" xi "+xi+" yi "+yi);					
		    //System.out.println("vasadroite "+vasadroite+" el[iq].xc_element "+el[iq].xc_element+" xrepos "+xrepos);					
		    if ((ddx<6*facx)&&(ddy <6*facy)){
			atomefixe=true;
			el[iq].fixe=!el[iq].fixe;
			if(el[iq].fixe)fixer_cdm=false;
			fin_gere_menus_souris=true;
			fixer_element=false;command="";
			if(el[iq].fixe)
			    comment="l'element  "+ iq+ " de cet ensemble  est fixe";
			else
			    comment="l'element  "+ iq+ " de cet ensemble n ' est plus fixe ";
			stopper=false;
			init_cond(false,true);
			barre_des_menus();
		    }
		}
	    }
	}
	if ((command=="preparer un etat initial immobile")||(command=="tirer de nouveau sur la chaine")){
	    iprepare++;
	    if(iprepare==1){
		stopper=true;
		init_cond(true,true);
		System.out.println(" on a remis à zero ");
	    }
	    modifier=true;
	    tire_la_chaine=(command=="tirer de nouveau sur la chaine");
	    if(!tire_la_chaine)
		comment="Deplacez les elements, cliquez dans le carre rouge en haut a gauche pour finir";
	    else
		comment="Deplacez un element, les autres suivront";
	    boolean ret=false;
	    if(draguee||trouve_deplacement)    
		    ret=glisser_vibr();
	    if(pressee){
		System.out.println("avant glisser ");
		ret=glisser_vibr();
	    }
	    if(relachee){
		ret=glisser_vibr();
		if(tire_la_chaine){
		    stopper=false;command="";comment="";
		    tire_la_chaine=false;iprepare=0;
		    fin_gere_menus_souris=true;
		    if(numero_ensemble==0){
			subject.calcule_coefficients();
			init_cond(false,true);
		    }
		    System.out.println("kkkkkksubject.ensemble[1].el[10].yreel "+subject.ensemble[1].el[10].yreel+" ret "+ret);
		    subject.ensemble[1].desactiver=false;
		    /*	
		    zoom_y=2;
		    facy=determine_zoom(zoom_y);
		    subject.ensemble[1-numero_ensemble].zoom_y=2;
		    subject.ensemble[1-numero_ensemble].facy=determine_zoom(subject.ensemble[1-numero_ensemble].zoom_y);
		    System.out.println("numero_ensemble "+numero_ensemble+" zoom_y "+zoom_y+" facy "+facy);
		    System.out.println(" subject.ensemble[1].zoom_y "+subject.ensemble[1].zoom_y+" subject.ensemble[1].facy "+subject.ensemble[1].facy);
		    System.out.println(" ensemble[1].el[10].yreel "+subject.ensemble[1].el[10].yreel);
		    */
		}
	    }
	    if(cliquee&&!tire_la_chaine)
		if((ppmouseh>30)&&(ppmouseh<80)&&(ppmousev>30)&&(ppmousev<80)){
		    fin_gere_menus_souris=true;
		    stopper=false;command="";iprepare=0;
		    tire_la_chaine=false;comment="";
		    init_cond(false,true);
		}
	    //    System.out.println("L' etat initial est pret, le systeme demarre");
	    }
	if (command=="faire le graphe d'un element"){
	    modifier=true;
	    comment="Cliquez sur un element pour voir son graphe";
	    graphe=!graphe_on;
	    System.out.println("graphe "+graphe+" graphe_on "+graphe_on+" achoisi "+achoisi);
	    if((achoisi>=0)&&graphe){
		courbe=new graphique("Graphe de l'element "+achoisi,true,1);
		graphe_on=true;
		command="";	
	    }
	}
	//if(modifier)return false;
	//return dg;
	return fin_gere_menus_souris;
    }

    public void	traite_click(){
	while(subject.occupied){
	    System.out.println("*numero_ensemble "+numero_ensemble+" cliquee "+cliquee);
	}
	if(!(subject.second_ensemble_virtuel&&(i_demarre==7||i_demarre==2)&&numero_ensemble==1)){
	    //System.out.println("entree traite_click "+ppmouseh);
	    if(cliquee){
		Date maintenant=new Date();
		subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	    }
	    command_prec=command;
	    boolean succes_menus=false;
	    if(command!="")
		succes_menus=gere_menus_souris();
	    //System.out.println("fin_gere_menus_souris "+fin_gere_menus_souris);
	    //System.out.println(" dans le traite click  3");
	    if(!succes_menus){
		if(draguee||trouve_deplacement)
		    ret=glisser_vibr();
		if(relachee&&trouve_deplacement)
		    ret=glisser_vibr();
		if(pressee){
		    subject.ensemble[1-numero_ensemble].desactiver=true;
		    if(i_demarre!=-1){
			ret=glisser_vibr();
			//if(!ret)
			// desactiver=true;
		    }else
			desactiver=true;
		}else{
		    subject.ensemble[1-numero_ensemble].desactiver=false;
		    if(i_demarre==-1)
			desactiver=false;
		}
		//System.out.println("dans le traite click  6 ");
		dg=gerelescarres();
		//System.out.println("dans le traite click  7 ");
	    }  
	}
    }
    public boolean glisser_vibr (){
      int	xi, yi, i, iq, iii;boolean ret=false;
      xi=ppmouseh;yi=ppmousev;
      //System.out.println("xi "+xi+" yi"+yi+" pressee "+pressee+"trouve_deplacement"+trouve_deplacement);
      if (pressee){
	  //System.out.println(" toto");
	  if(!trouve_deplacement){
	      for(iq=0;iq<nb_el_ens;iq++){
		  System.out.println("iq cherche "+iq);
		  el[iq].deplacer=false;
		  double ddx=Math.abs(xi-vasadroite-((el[iq].xc_element-xrepos)*facx+xrepos));
		  double ddy=Math.abs(yi-((el[iq].yc_element-yrepos)*facy+yrepos));
		  if ((ddx<10*facx)&&(ddy <10*facy)){
		      comment="atome trouve "+iq;
		      trouve_deplacement=true;
		      el[iq].deplacer=true;
		      iq_dep=iq;
		      if (tire_la_chaine){
			  el[0].fixe=true;
			  el[nb_el_ens-1].fixe=true;
		      }
		      atome_glisse=iq;
		      xrepo_dep=xrepos+el[iq].xreel0/dimensionpixel;
		      dx_pt_souris=-((el[iq].xc_element-xrepo_dep)*facx+xrepo_dep)+xi;
		      dy_pt_souris=-((el[iq].yc_element-yrepos)*facy+yrepos)+yi;
		      System.out.println(" *****************deplacement initial, iq "+iq);
		      achoisi=iq;
		      break;
		  }
	      }
	  }
      }
      if(trouve_deplacement){
	  //System.out.println("&&&&&&&&&&&&&&&&&&&&&& relachee "+relachee+"  iq_dep "+iq_dep);
	  if(relachee){
	      System.out.println("trouve_deplacement "+trouve_deplacement+" relachee "+relachee);
	      deplacement(xi, yi, iq_dep );
	      ret=true;
	      trouve_deplacement=false;
	      for(iq=0;iq<nb_el_ens;iq++)
		  el[iq].deplacer=false;
	  }else if(draguee){
	      //System.out.println("trouve_deplacement "+trouve_deplacement+" draguee "+draguee);
	      deplacement(xi, yi, iq_dep);
	  }
      }
      return ret;
    }
    public void deplacement(double xi,double yi, int iq){
	//System.out.println(" entree dans deplacement ");
	int	i, jq ;
	double	xep, yep,  ax, ay;
	if(relachee||draguee){
	    if(oscillations_transverses){
		//ay=(int) Math.round((ppmousev-dy_pt_souris -yrepos)/facy);
		ay=(int) Math.round((ppmousev-dy_pt_souris -yrepos)/facy);
		el[iq].yreel=ay*dimensionpixel;
		el[iq].yc_element=yrepos+(int) Math.round((el[iq].yreel+el[iq].yreel0)/dimensionpixel);
		//System.out.println("ay "+ay+"el[iq].yreel  "+el[iq].yreel+"el[iq].yc_element"+el[iq].yc_element);
		//System.out.println(" draguee "+draguee+" iq "+iq+" el[iq].yreel "+el[iq].yreel);
	    }else{
		//ax=round((pp.h-dx_pt_souris-xrepo_dep)/facx);
		ax=(int) Math.round((ppmouseh-dx_pt_souris-xrepo_dep)/facx);
		el[iq].xreel=ax*dimensionpixel;
		el[iq].xc_element=(int) Math.round(xrepo_dep+ax);
	    }
	    if(tire_la_chaine){
		for(jq=1;jq<nb_el_ens-1;jq++){
		    if (jq<atome_glisse)
			el[jq].yreel=(el[atome_glisse].yreel-el[0].yreel)/(el[atome_glisse].xreel0- el[0].xreel0)*(el[jq].xreel0-el[0].xreel0);
		    if (jq>atome_glisse)
			el[jq].yreel=(el[atome_glisse].yreel-el[nb_el_ens-1].yreel)/(el[atome_glisse].xreel0 -el[nb_el_ens-1].xreel0)*(el[jq].xreel0-el[nb_el_ens-1].xreel0);
		    if (jq !=atome_glisse)
			el[jq].yc_element=yrepos+(int) Math.round((el[jq].yreel0+el[jq].yreel)/dimensionpixel);
		    if(jq==10)
			System.out.println(" jq "+jq+" el[jq].yc_element "+el[jq].yc_element);
		}
	    }
	}
	//      until !button;
    } 
    class graphique extends Frame{
	final int top=100;final int left=100;final int bottom=400;final int right=500;double elong[]=new double[401];Graphics grp;
	boolean occupe=false,maximum_atteint=false;double reduc=1.,elo_reduc=0.;
	int n_pts_graphe=0,temps_graphe=10;boolean elem;double elong_max=0;int num_graphe;
	public graphique(String s, boolean elem1, int numg){
	    super(s);elem=elem1;num_graphe=numg;
	    System.out.println("graphique ");
	    if(!demonst)
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    while (occupe||subject.occupied){
				System.out.println("sortie du graphiqe elem "+elem);	    			    }
			    fen_energie.dispose();
			    fen_energie=null;
			};
		    });
	    
	    setSize(right-left,bottom-top);
	    setLocation(left+numg*30,top+numero_ensemble*100+numg*30);
	    //setLocation(left,top);
	    setVisible(true);
	    grp=getGraphics(); 
	    grp.setColor(Color.blue);
	    elong_max=1.0;
	    System.out.println("grp "+grp);
	}
	void dessine_graphe (){
	    occupe=true;
	    int elongy,npts;
	    if(oscillations_forcees_sinusoidales||oscil_composees||oscil_paquet){
		if(frequence_centrale>frequence_min){
		    n_pts_graphe++;
		    if(n_pts_graphe==10)
			n_pts_graphe=1;
		}
	    }else
		n_pts_graphe=1;
	    if (n_pts_graphe==1){
		if(elem)
		    if (oscillations_transverses){
			elong[temps_graphe]=yrepos+ Math.round(((el[achoisi].yreel0+el[achoisi].yreel-yyyy)/dimensionpixel)*facy);
			System.out.println("temps_graphe "+temps_graphe+" elong[temps_graphe] "+elong[temps_graphe]);

		    }else 
			elong[temps_graphe]=xrepos+ Math.round(((el[achoisi].xreel0+el[achoisi].xreel-xxxx)/dimensionpixel)*facy);
		else{
		    double elo=energie_totale()*1.0E19;
		    double elong_maxp=elong_max;
		    maximum_atteint=elo>elong_max;
		    if(elo>elong_max){
			elong_max=elo;
			for(int i=0;i<=temps_graphe;i++){
			    double eloo=-(elong[i]-(bottom-top-30))*elong_maxp/(bottom-top-60);
			    elong[i]=bottom-top-30-(eloo*(bottom-top-60)/elong_max);
				//System.out.println(" elong[i] "+elong[i]+" elong_max "+elong_max+" elong_maxp "+elong_maxp);
				//System.out.println(" eloo "+eloo);
			}
		    }else			
			elong[temps_graphe]=bottom-top-30-(elo*(bottom-top-60)/elong_max);
		}
		if(elem){
		    if(Math.abs(elong[temps_graphe]-yrepos)*reduc<(bottom-top-40)/2)
			for (int j=-1;j<=1;j++){
			    elo_reduc=yrepos+reduc*(elong[temps_graphe]-yrepos);
			    grp.drawLine(temps_graphe-1,(int)elo_reduc+j,temps_graphe+1,(int)elo_reduc+j);
			    //else if (!elem){
			}
		    else{
			reduc*=0.7;
			subject.eraserect(grp,0,0,bottom-top,right-left);
			grp.setColor(Color.blue);
			for(int i=10;i<=temps_graphe;i++){
			    elo_reduc=yrepos+reduc*(elong[i]-yrepos);
			    for (int j=-1;j<=1;j++)
				grp.drawLine(i-1,(int)elo_reduc+j,i+1,(int)elo_reduc+j);
			}
		    }
		}else{
		    if(!maximum_atteint){
			elo_reduc=elong[temps_graphe];
			for (int j=-1;j<=1;j++)
			    grp.drawLine(temps_graphe-1,(int)elo_reduc+j,temps_graphe+1,(int)elo_reduc+j);
		    }else{
			subject.eraserect(grp,0,0,bottom-top,right-left);
			grp.setColor(Color.blue);
			for(int i=10;i<=temps_graphe;i++){
			    elo_reduc=elong[i];
			    for (int j=-1;j<=1;j++)
				grp.drawLine(i-1,(int)elo_reduc+j,i+1,(int)elo_reduc+j);
			}
		    }
		}
		temps_graphe++;
		if (temps_graphe==right-left){
		    temps_graphe=10;
		    subject.eraserect(grp,0,0,bottom-top,right-left);
		    grp.setColor(Color.blue);
		}
	    occupe=false;
	    //System.out.println(" temps_graphe "+temps_graphe+" elong[temps_graphe] "+elong[temps_graphe]);
	    }
	}
    }
    /*
    class keyList extends KeyAdapter{ 
	int st;int carac_code;int puiss;
	public void keyList(){
	    key_entered=false;puiss=1;st=0;
	}
	public void keyPressed(KeyEvent k){
	    //st=k.getKeyCode()-48;key_entered=true;System.out.println("key "+st);
	    //gere_key();
	    carac_code=k.getKeyCode();System.out.println("carac_code  "+carac_code);
	    if(carac_code==KeyEvent.VK_DELETE||carac_code==10){
		key_entered=true;
		gere_key();tf.setText("");System.out.println(st);
		puiss=1;st=0;key_entered=false;
	    }else{
		puiss*=10;st=st*puiss+(k.getKeyCode()-48);
		System.out.println("st  "+st);
	    }
	}
	public void gere_key(){
	    if (command=="preparer une onde stationnaire"){
		oscillations_forcees_sinusoidales=false;
		if(key_entered){
		    if (el[0].fixe==el[nb_el_ens-1].fixe){				   
			n_demi=st;
			if (n_demi>nb_el_ens-1){
			    comment="Impossible, choisissez un nombre plus petit!";
			}
			if(!el[0].fixe)
			    lambda=2*nb_el_ens*distance_inter_atomes/n_demi;
			else
			    lambda=2*(nb_el_ens-1)*distance_inter_atomes/n_demi;
		    }
		    else{
			n_quart=st;
			if (n_quart>2*(nb_el_ens-1)){
			    comment="Impossible, choisissez un nombre impair ou plus petit!";
			}
			lambda=4*(nb_el_ens- 0.5)*distance_inter_atomes/n_quart;
		    }
		    demarre_onde_stationnaire();
		}		
	    }
	}
    }
    */
    class MouseMotion extends MouseMotionAdapter
    {
	//    Applet subject;
	public MouseMotion (ensemble_de_chaines a)
	{
	    // subject=a;
	}
	public void mouseMoved(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=false;}
	public void mouseDragged(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	System.out.println("draguee dans Mousemove "+draguee);
	traite_click();
	}
    }
    
    class MouseStatic extends MouseAdapter
    {
	//    Applet subject;
	public MouseStatic (ensemble_de_chaines a)
	{
	    //      subject=a;
	}
	public void mouseClicked(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;pressee=false;relachee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ensemble[ichaine].nb_el_ens "+ensemble[ichaine].nb_el_ens);
	    //System.out.println("ichaine "+ichaine);
	    //for( int iq=0;iq<ensemble[ichaine].nb_el_ens;iq++)
	    //ensemble[ichaine].el[iq].deplacer=false;
	}
	public void mousePressed(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=false;pressee=true;relachee=false;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=false;pressee=false;relachee=true;
	    System.out.println("relachee "+relachee);
	    traite_click();
	}
    }
}
/*class Actif1 extends Thread
//{Applet subject;n
//  Actif1(Applet a)
//  {subject=a;
//  }
//  public void run()
//  {
//  while (true)
//    {
//      System.out.println("Actif1");
//	 subject.menu_principal_ou_fin(gr);
//	 //          for (int i=0;i<2;i++)
//	 //{
//      System.out.println("i ");
//       subject.repain();
      //}
//    try {Thread.sleep(20);}
//    catch (InterruptedException signal){}
//    }
//  }

*/





