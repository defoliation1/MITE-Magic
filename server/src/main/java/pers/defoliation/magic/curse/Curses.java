package pers.defoliation.magic.curse;

public class Curses {

    public static final RustCurse rust = new RustCurse();
    public static final AsocialCurse asocial = new AsocialCurse();
    public static final BlasterCurse blaster = new BlasterCurse();
    public static final BloodthirstyCurse bloodthirsty = new BloodthirstyCurse();
    public static final BulkyCurse bulky = new BulkyCurse();
    public static final EnvyCurse envy = new EnvyCurse();
    public static final GluttonyCurse gluttony = new GluttonyCurse();
    public static final LygophobiaCurse lygophobia = new LygophobiaCurse();
//    public static final Enchantment pollution;
    public static final PrideCurse pride = new PrideCurse();
    public static final SlothCurse sloth = new SlothCurse();
    public static final SocialCurse social = new SocialCurse();
    public static final WeakCurse weak = new WeakCurse();

    private static boolean registered = false;

    public static void registerAll(){
        if(registered)
            return;
        CurseManager curseManager = CurseManager.INSTANCE;
        curseManager.registerCurse(rust);
        curseManager.registerCurse(asocial);
        curseManager.registerCurse(blaster);
        curseManager.registerCurse(bloodthirsty);
        curseManager.registerCurse(bulky);
        curseManager.registerCurse(envy);
        curseManager.registerCurse(gluttony);
        curseManager.registerCurse(lygophobia);
        curseManager.registerCurse(sloth);
        curseManager.registerCurse(pride);
        curseManager.registerCurse(social);
        curseManager.registerCurse(weak);
        registered = true;
    }

}
