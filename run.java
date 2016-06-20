

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;


public class run {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("example2.csv"));
		BufferedReader reader = new BufferedReader(new FileReader("./train.csv"));
		
		HashMap<String,ArrayList<String>> hash = new HashMap<>();
		
		HashMap<String,HashMap<String,Integer>> cs_hash=new HashMap<>();
		HashMap<String,HashMap<String,Integer>> cap_hash=new HashMap<>();
		HashMap<String,HashMap<String,Integer>> cdw_hash=new HashMap<>();
		HashMap<String,HashMap<String,Integer>> cp_hash=new HashMap<>();
		HashMap<String,HashMap<String,Integer>> ca_hash=new HashMap<>();
		
		String line = reader.readLine();
		StringTokenizer token;
		int size = 0;
		while((line = reader.readLine()) != null){
			size++;
			token = new StringTokenizer(line,",");
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<String> value = new ArrayList<String>();
			HashMap<String,Integer> se = new HashMap<>();
			HashMap<String,Integer> ap = new HashMap<>();
			HashMap<String,Integer> week = new HashMap<>();
			HashMap<String,Integer> pd = new HashMap<>();
			HashMap<String,Integer> ad = new HashMap<>();
			
			while(token.hasMoreTokens()){
				boolean flag=true;				
				String temp =token.nextToken();
				if(temp.contains("\"")){
					String v=temp;
					while(flag){
						temp=token.nextToken();
						v+=temp;
						if(temp.contains("\""))
							flag=false;
					}
					value.add(v);
				}
				else
					value.add(temp);
			}
			
			
			String dates=value.get(0);
			String ampm;
			String category=value.get(1);
			String dayofweek=value.get(3);
			String pddistrict=value.get(4);
			String addr=value.get(6);
			String addr2=value.get(6);
			
			int d= Integer.parseInt(dates.split(" ")[0].split("-")[1]);
			int t= Integer.parseInt(dates.split(" ")[1] .split(":")[0]);
			
			//ap pm
			/*
			if(t<12)
				ampm="am";
			else
				ampm="pm";
			*/
			/*
			if(t<6)
				ampm = "aam";
			else if(t<12)
				ampm="am";
			else if(t<18)
				ampm = "ppm";
			else
				ampm="pm";
			*/
			ampm = t+"";
			String season;
			/*
			if(3<=d&&d<6)
				season="Spring";
			else if(6<=d&&d<9)
				season="Summer";
			else if(9<=d&&d<12)
				season="Fall";
			else
				season="Winter";
			*/
			season = d+"";
			
			
			// 주소 / 로 나누
			if(addr.split("/").length==2){
				addr2=addr.split("/")[1];
				if(addr.split("/")[1].split(" ").length==2)
					addr2=addr.split("/")[1].split(" ")[1];
				else{
					int n =addr.split("/")[1].split(" ").length;
					addr2 = addr.split("/")[1].split(" ")[n-2];
				}
				String val = season+","+ampm+","+dayofweek+","+pddistrict+","+addr2;
				
				if(hash.containsKey(category)){
					hash.get(category).add(val);
				}else{
					list.add(val);
					hash.put(category, list);
				}
				
				//Category Address2
				if(ca_hash.containsKey(category)){
					if(ca_hash.get(category).containsKey(addr2)){
						int an = 0;
						an=ca_hash.get(category).get(addr2);
						ca_hash.get(category).put(addr2, an+1);
					}
					else{
						ca_hash.get(category).put(addr2, 1);
					}
				}else{
					ad.put(addr2, 1);
					ca_hash.put(category, ad);
				}
				
			}
			
			if(addr.split("/")[0].split(" ").length==1)
				addr=addr.split("/")[0].split(" ")[0];
			else{
				int n =addr.split("/")[0].split(" ").length;
				addr=addr.split("/")[0].split(" ")[n-2];
			}
			
			String val = season+","+ampm+","+dayofweek+","+pddistrict+","+addr;
			
			if(hash.containsKey(category)){
				hash.get(category).add(val);
			}else{
				list.add(val);
				hash.put(category, list);
			}	
			
			//Category season
			if(cs_hash.containsKey(category)){
				if(cs_hash.get(category).containsKey(season)){
					int sn =0;
					sn=cs_hash.get(category).get(season);
					cs_hash.get(category).put(season, sn+1);
				}
				else{
					cs_hash.get(category).put(season, 1);
				}
			}else{
				se.put(season, 1);
				cs_hash.put(category, se);
			}
			
			//Category ampm
			if(cap_hash.containsKey(category)){
				if(cap_hash.get(category).containsKey(ampm)){
					int apn = 0;
					apn=cap_hash.get(category).get(ampm);
					cap_hash.get(category).put(ampm, apn+1);
				}
				else{
					cap_hash.get(category).put(ampm, 1);
				}
			}else{
				ap.put(ampm, 1);
				cap_hash.put(category, ap);
			}
			
			//Category DayofWeek
			if(cdw_hash.containsKey(category)){
				if(cdw_hash.get(category).containsKey(dayofweek)){
					int dwn = 0;
					dwn=cdw_hash.get(category).get(dayofweek);
					cdw_hash.get(category).put(dayofweek, dwn+1);
				}
				else{
					cdw_hash.get(category).put(dayofweek, 1);
				}
			}else{
				week.put(dayofweek, 1);
				cdw_hash.put(category, week);
			}
			
			//Category Pddistrict
			if(cp_hash.containsKey(category)){
				if(cp_hash.get(category).containsKey(pddistrict)){
					int pdn = 0;
					pdn=cp_hash.get(category).get(pddistrict);
					cp_hash.get(category).put(pddistrict, pdn+1);
				}
				else{
					cp_hash.get(category).put(pddistrict, 1);
				}
			}else{
				pd.put(pddistrict, 1);
				cp_hash.put(category, pd);
			}
			
			//Category Address
			if(ca_hash.containsKey(category)){
				if(ca_hash.get(category).containsKey(addr)){
					int an = 0;
					an=ca_hash.get(category).get(addr);
					ca_hash.get(category).put(addr, an+1);
				}
				else{
					ca_hash.get(category).put(addr, 1);
				}
			}else{
				ad.put(addr, 1);
				ca_hash.put(category, ad);
			}
			se.clear();
			ap.clear();
			week.clear();
			pd.clear();;
			ad.clear();;
		}
		
		BufferedReader br = new BufferedReader(new FileReader("./test.csv"));
		
		Iterator<String> it = hash.keySet().iterator();
		String str = "Id,";
		while(it.hasNext()) str = str+it.next()+",";
		
		bw.write(str.substring(0,str.length()-1));
		bw.newLine();
		br.readLine();
		
		while((line=br.readLine())!=null){
			String[] at = line.split(",");
			String id = at[0];
			String dates = at[1];
			String ampm;
			String season;
			String word = id+",";
			
			int d= Integer.parseInt(dates.split(" ")[0].split("-")[1]);
			int t= Integer.parseInt(dates.split(" ")[1] .split(":")[0]);
			
			//ampm
			/*
			if(t<12)
				ampm="am";
			else
				ampm="pm";
			*/
			/*
			if(t<6)
				ampm = "aam";
			else if(t<12)
				ampm="am";
			else if(t<18)
				ampm = "ppm";
			else
				ampm="pm";
			*/
			ampm = t+"";
			//season
			/*
			if(3<=d&&d<6)
				season="Spring";
			else if(6<=d&&d<9)
				season="Summer";
			else if(9<=d&&d<12)
				season="Fall";
			else
				season="Winter";
			*/
			season = d+"";
			String dayofweek=at[2];
			String pddistrict=at[3];
			String addr=at[4];
			String addr2="";
			
			//주소 / 로 나누
			if(addr.split("/").length==2){
				addr2=addr.split("/")[1];
				if(addr.split("/")[1].split(" ").length==2)
					addr2=addr.split("/")[1].split(" ")[1];
				else{
					int n =addr.split("/")[1].split(" ").length;
					addr2 = addr.split("/")[1].split(" ")[n-2];
				}
			}
			if(addr.split("/")[0].split(" ").length==1)
				addr=addr.split("/")[0].split(" ")[0];
			else{
				int n =addr.split("/")[0].split(" ").length;
				addr=addr.split("/")[0].split(" ")[n-2];
			}
			
			int cnt = 0;
			
			//각 Category 마다 확률 구하
			for(String key : hash.keySet()){
				
				double[] p = new double[hash.size()];
				ArrayList<String> list = hash.get(key);
				int cate_size = list.size();
				double Pp = cate_size/(double)size;
				int adr1=0;
				int adr2=0;
				int s=0;
				int apm=0;
				int dw=0;
				int pdd=0;
				
				if(cs_hash.get(key).containsKey(season))
					s=cs_hash.get(key).get(season);
				if(cap_hash.get(key).containsKey(ampm))
					apm=cap_hash.get(key).get(ampm);
				if(cdw_hash.get(key).containsKey(dayofweek))
					dw=cdw_hash.get(key).get(dayofweek);
				if(cp_hash.get(key).containsKey(pddistrict))
					pdd=cp_hash.get(key).get(pddistrict);
				if(ca_hash.get(key).containsKey(addr))
					adr1=ca_hash.get(key).get(addr);
				if(ca_hash.get(key).containsKey(addr2))
					adr2=ca_hash.get(key).get(addr2);
				
				double value = 1;
				value = value
					* (s/(double)cate_size)
					* (apm/(double)cate_size)
					* (dw/(double)cate_size)
					* (pdd/(double)cate_size)
					* ((adr1+adr2)/(double)cate_size);
				
				p[cnt] = Pp * value;
				word = word+p[cnt]+",";
				cnt++;
				
			}
			bw.write(word.substring(0,word.length()-1));
			bw.newLine();
		}
		bw.close();
	}
}
