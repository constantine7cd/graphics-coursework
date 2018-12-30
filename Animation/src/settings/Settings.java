package settings;

import structures.Vector4f;
import utils.MFile;

public class Settings {
	
	public static final MFile RES_FOLDER = new MFile("res");

	public static final String DIFFUSE_FILE = "Resources/res/DIFF10.png";

	public static final String LETTER_A = "letter_a.dae";
	public static final String LETTER_B = "letter_b.dae";
	public static final String LETTER_V = "letter_v.dae";
	public static final String LETTER_G = "letter_g.dae";
	public static final String LETTER_D = "letter_d.dae";
	public static final String LETTER_E = "letter_e.dae";
	public static final String LETTER_Yo = "letter_yo.dae";
	public static final String LETTER_J = "letter_j.dae";
	public static final String LETTER_Z = "letter_z.dae";
	public static final String LETTER_I = "letter_i.dae";
	public static final String LETTER_I_ = "letter_i_.dae";
	public static final String LETTER_K = "letter_k.dae";
	public static final String LETTER_L = "letter_l.dae";
	public static final String LETTER_M = "letter_m.dae";
	public static final String LETTER_N = "letter_n.dae";
	public static final String LETTER_O = "letter_o.dae";
	public static final String LETTER_P = "letter_p.dae";
	public static final String LETTER_R = "letter_r.dae";
	public static final String LETTER_S = "letter_s.dae";
	public static final String LETTER_T = "letter_t.dae";
	public static final String LETTER_U = "letter_u.dae";
	public static final String LETTER_F = "letter_f.dae";
	public static final String LETTER_H = "letter_h.dae";
	public static final String LETTER_C = "letter_c.dae";
	public static final String LETTER_Ch = "letter_ch.dae";
	public static final String LETTER_Sh = "letter_sh.dae";
	public static final String LETTER_Sch = "letter_sch.dae";
	public static final String LETTER_TZ = "letter_tz.dae";
	public static final String LETTER_II = "letter_ii.dae";
	public static final String LETTER_MZ = "letter_mz.dae";
	public static final String LETTER_EE = "letter_ee.dae";
	public static final String LETTER_Yu = "letter_yu.dae";
	public static final String LETTER_Ya = "letter_ya.dae";

	public static final String[] LETTERS_SOURCES = { LETTER_A, LETTER_B,  LETTER_V, LETTER_G, LETTER_D,
			LETTER_E, LETTER_Yo, LETTER_J, LETTER_Z, LETTER_I, LETTER_I_, LETTER_K, LETTER_L, LETTER_M,
			LETTER_N, LETTER_O, LETTER_P, LETTER_R, LETTER_S, LETTER_T, LETTER_U, LETTER_F, LETTER_H,
			LETTER_C, LETTER_Ch, LETTER_Sh, LETTER_Sch, LETTER_TZ, LETTER_II, LETTER_MZ, LETTER_EE,
			LETTER_Yu, LETTER_Ya};

	public static final String[] LETTERS = { "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й",
            "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ",
            "Ъ", "Ы", "Ь", "Э", "Ю", "Я"};


    public static final char[] LETTERS_C = { 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};


	public static final byte SCENE_BACKGROUND = (byte) 0x00;

	public static final float INC_TIME =  (float) 0.02;
	
	public static final int MAX_WEIGHTS = 3;

    public static Vector4f LIGHT_DIR = new Vector4f(0, 0, -1);
	
}
