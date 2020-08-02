package com.example.alzheimers_detection;

class ListsForSpinner {

    //days of week
    public String[] weekDays() {
       return new String[]{"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    //states and UTs in india
   public String[] states() {
        return new String[]{"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
                "Chattisgarh", "Dadra & Nagar Haveli and Daman & Diu", "Delhi", "Goa", "Gujurat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir",
                "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh",
                "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
                "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    }


    //List of Cities
    //https://www.britannica.com/topic/list-of-cities-and-towns-in-India-2033033
    public String[] getCitiesArray(String state) {
            String[] cities = null;
        if (state.compareToIgnoreCase("Andaman and Nicobar Islands") >= 0 && state.compareToIgnoreCase("Goa") <= 0) {
            switch (state) {
                case "Andaman and Nicobar Islands": cities = new String[]{"Andaman and Nicobar Islands","Port Blair"};
                    break;

                case "Andhra Pradesh": cities = new String[]{"Adoni","Amaravati", "Anantapur", "Chandragiri", "Chittoor",
                        "Dowlaiswaram", "Eluru", "Guntur", "Kadapa", "Kakinada", "Kurnool", "Machilipatnam", "Nagarjunakoṇḍa",
                        "Rajahmundry", "Srikakulam", "Tirupati", "Vijayawada", "Visakhapatnam", "Vizianagaram", "Yemmiganur"};
                    break;

                case "Arunachal Pradesh": cities = new String[]{"Anjaw", "Changlang", "Dibang Valley", "East Kameng", "East Siang", "Kamle", "Kra Daadi", "Kurung Kumey", "Lepa Rada", 
                    "Lohit", "Longding", "Lower Dibang Valley", "Lower Siang", "Lower Subansiri", "Namsai", "Pakke Kessang", "Papum Pare", "Shi Yomi", "Siang", "Tawang", "Tirap",
                     "Upper Siang", "Upper Subansiri", "West Kameng", "West Siang"};
                    break;

                case "Assam": cities = new String[]{"Dhuburi","Dibrugarh", "Dispur", "Guwahati", "Jorhat",
                        "Nagaon", "Sibsagar", "Silchar", "Tezpur", "Tinsukia"};
                    break;

                case "Bihar": cities = new String[]{"Ara", "Baruni", "Begusarai", "Bettiah", "Bhagalpur", "Bihar Sharif",
                        "Bodh Gaya", "Buxar", "Chapra", "Darbhanga","Dehri", "Dinapur Nizamat", "Gaya", "Hajipur", "Jamalpur",
                        "Katihar", "Madhubani", "Motihari", "Munger", "Muzaffarpur", "Patna", "Purnia", "Pusa", "Saharsa",
                        "Samastipur", "Sasaram", "Sitamarhi", "Siwan",};
                    break;

                case "Chandigarh": cities = new String[]{"Chandigarh"};
                    break;

                case "Chattisgarh": cities = new String[]{"Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", "Bijapur", "Bilaspur", "Dantewada (South Bastar)",
                       "Dhamtari", "Durg", "Gariyaband", "Janjgir-Champa", "Jashpur", "Kabirdham (Kawardha)", "Kanker (North Bastar)", "Kondagaon", "Korba", "Korea (Koriya)", 
                     "Mahasamund", "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Sukma", "Surajpur", "Surguja"};
                    break;

                case "Dadra & Nagar Haveli and Daman & Diu": cities = new String[]{"Silvassa","Daman", "Diu","Dadra & Nagar Haveli"};
                    break;

                case "Delhi": cities = new String[]{"Central Delhi", "East Delhi", "New Delhi", "North Delhi", "North East Delhi", "North West Delhi", "Shahdara", "South Delhi", 
                     "South East Delhi", "South West Delhi", "West Delhi"};
                    break;

                case "Goa": cities = new String[]{"North Goa", "South Goa"};
                    break;
            }
        } else if (state.compareToIgnoreCase("Gujurat") >= 0 && state.compareToIgnoreCase("Ladakh") <= 0) {
            switch (state) {
                case "Gujurat": cities = new String[]{"Ahmadabad", "Amreli", "Bharuch", "Bhavnagar", "Bhuj", "Dwarka", "Gandhinagar", "Godhra",
                        "Jamnagar", "Junagadh", "Kandla", "Khambhat", "Kheda", "Mahesana", "Morvi","Nadiad", "Navsari", "Okha", "Palanpur",
                        "Patan", "Porbandar", "Rajkot", "Surat", "Surendranagar", "Valsad", "Veraval"};
                    break;

                case "Haryana": cities = new String[]{"Ambala", "Bhiwani", "Chandigarh", "Faridabad", "Firozpur Jhirka", "Gurgaon", "Hansi",
                        "Hisar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Panipat", "Pehowa", "Rewari", "Rohtak", "Sirsa", "Sonipat"};
                    break;

                case "Himachal Pradesh": cities = new String[]{"Bilaspur", "Chamba", "Dalhousie", "Dharmshala", "Hamirpur", "Kangra",
                        "Kullu", "Mandi","Nahan", "Shimla", "Una"};
                    break;

                case "Jammu and Kashmir": cities = new String[]{"Anantnag", "Baramula", "Doda", "Gulmarg", "Jammu", "Kathua", "Leh", "Punch",
                        "Rajauri", "Srinagar", "Udhampur"};
                    break;

                case "Jharkhand": cities = new String[]{"Bokaro", "Chaibasa", "Deoghar", "Dhanbad", "Dumka", "Giridih", "Hazaribag", "Jamshedpur",
                        "Jharia", "Rajmahal", "Ranchi", "Saraikela"};
                    break;

                case "Karnataka": cities = new String[]{"Badami", "Ballari", "Bangalore", "Belgavi", "Bhadravati", "Bidar", "Chikkamagaluru",
                        "Chitradurga", "Davangere", "Halebid", "Hassan", "Hubballi-Dharwad", "Kalaburagi", "Kolar", "Madikeri", "Mandya", "Mangaluru",
                        "Mysuru", "Raichur", "Shivamogga", "Shravanabelagola", "Shrirangapattana", "Tumkuru"};
                    break;

                case "Kerala": cities = new String[]{"Alappuzha", "Badagara", "Ernakulam", "Idukki", "Kannur", "Kochi", "Kollam", "Kottayam", "Kozhikode", "Mattancheri",
                        "Palakkad", "Thalassery", "Thiruvananthapuram", "Thrissur"};
                    break;

                case "Ladakh": cities = new String[]{"Kargil","Leh"};
                    break;
            }
        } else if (state.compareToIgnoreCase("Lakshadeep") >= 0 && state.compareToIgnoreCase("Punjab") <= 0) {
            switch (state) {
                case "Lakshadweep": cities = new String[]{"Lakshadweep"};
                    break;
                case "Madhya Pradesh": cities = new String[]{"Balaghat", "Barwani", "Betul", "Bharhut", "Bhind", "Bhojpur", "Bhopal", "Burhanpur", "Chhatarpur",
                        "Chhindwara", "Damoh", "Datia", "Dewas", "Dhar", "Guna", "Gwalior", "Hoshangabad", "Indore", "Itarsi", "Jabalpur", "Jhabua", "Khajuraho",
                        "Khandwa", "Khargon", "Maheshwar", "Mandla", "Mandsaur", "Mhow", "Morena", "Murwara", "Narsimhapur", "Narsinghgarh", "Narwar", "Neemuch",
                        "Nowgong", "Orchha", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Sarangpur", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur",
                        "Sheopur", "Shivpuri", "Ujjain", "Vidisha"};
                    break;

                case "Maharashtra": cities = new String[]{"Ahmadnagar", "Akola", "Amravati", "Aurangabad", "Bhandara", "Bhusawal", "Beed", "Buldana", "Chandrapur",
                        "Daulatabad", "Dhule", "Jalgaon", "Kalyan", "Karli", "Kolhapur", "Mahabaleshwar", "Malegaon", "Matheran", "Mumbai", "Nagpur", "Nanded", "Nashik",
                        "Osmanabad", "Pandharpur", "Parbhani", "Pune", "Ratnagiri", "Sangli", "Satara", "Sevagram", "Solapur", "Thane", "Ulhasnagar", "Vasai-Virar",
                        "Wardha", "Yavatmal"};
                    break;

                case "Manipur": cities = new String[]{"Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", "Senapati", "Tamenglong", "Thoubal",
                        "Ukhrul", "Kangpokpi", "Tengnoupal", "Pherzawl", "Noney", "Kamjong", "Jiribam", "Kakching"};
                    break;

                case "Meghalaya": cities = new String[]{"East Garo Hills", "East Jaintia Hills", "East Khasi Hills", "North Garo Hills", "Ri Bhoi", "South Garo Hills", "South West Garo Hills",
                     "South West Khasi Hills", "West Garo Hills", "West Jaintia Hills", "West Khasi Hills"};
                    break;

                case "Mizoram": cities = new String[]{"Aizawl", "Champhai", "Kolasib", "Lawngtlai", "Lunglei", "Mamit", "Saiha", "Serchhip"};
                    break;
                case "Nagaland": cities = new String[]{"Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Peren", "Phek", "Tuensang", "Wokha", "Zunheboto"};
                    break;
                case "Odisha": cities = new String[]{"Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh", "Cuttack", "Deogarh", "Dhenkanal", "Gajapati", "Ganjam",
                      "Jagatsinghapur", "Jajpur", "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Kendujhar", "Khordha", "Koraput", "Malkangiri", "Mayurbhanj", 
                       "Nabarangpur", "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Sonepur", "Sundargarh"};
                    break;
                case "Puducherry": cities = new String[]{"Karaikal", "Mahe", "Puducherry", "Yanam"};
                    break;
                case "Punjab": cities = new String[]{"Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka", "Ferozepur", "Gurdaspur", "Hoshiarpur", 
                      "Jalandhar", "Kapurthala", "Ludhiana", "Mansa", "Moga", "Muktsar", "Nawanshahr", "Pathankot", "Patiala", "Rupnagar", "Sahibzada Ajit Singh Nagar (Mohali)", 
                       "Sangrur", "Tarn Taran"};
                    break;
            }
        } else {
            switch (state) {
                case "Rajasthan": cities = new String[]{"Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara", "Bikaner", "Bundi", "Chittorgarh", "Churu", 
                      "Dausa", "Dholpur", "Dungarpur", "Hanumangarh", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalore", "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota",
                      "Nagaur", "Pali", "Pratapgarh", "Rajsamand", "Sawai Madhopur", "Sikar", "Sirohi", "Sri Ganganagar", "Tonk", "Udaipur"};
                    break;
                case "Sikkim": cities = new String[]{"East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"};
                    break;
                case "Tamil Nadu": cities = new String[]{"Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kallakurichi", 
                      "Kanchipuram", "Kanyakumari", "Karur", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", 
                      "Ranipet", "Salem", "Sivaganga", "Tenkasi", "Thanjavur", "Theni", "Thoothukudi", "Tiruchirappalli", "Tirunelveli", "Tirupathur", "Tiruppur", "Tiruvallur",
                       "Tiruvannamalai", "Tiruvarur", "Vellore", "Viluppuram", "Virudhunagar"};
                    break;
                case "Telangana": cities = new String[]{"Adilabad", "Bhadradri", "Kothagudem", "Hyderabad", "Jagtial", "Jangaon", "Jayashankar", "Bhoopalpally", "Jogulamba GadwaL",
                       "Kamareddy", "Karimnagar", "Khammam", "Komaram Bheem", "Asifabad", "Mahabubabad", "Mahabubnagar", "Mancherial", "Medak", "Medchal", "Nagarkurnool", 
                         "Nalgonda", "Nirmal",  "Nizamabad", "Peddapalli", "Rajanna Sircilla", "Rangareddy", "Sangareddy", "Siddipet", "Suryapet", "Vikarabad", "Wanaparthy",
                        "Warangal (Rural)", "Warangal (Urban)", "Yadadri", "Bhuvanagiri"};
                    break;
                case "Tripura": cities = new String[]{"Dhalai", "Gomati", "Khowai", "North Tripura", "Sepahijala", "South Tripura", "Unakoti", "West Tripura"};
                    break;
                case "Uttar Pradesh": cities = new String[]{"Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "	Amethi", "Amroha", "Auraiya", "	Azamgarh", "Baghpat", "Bahraich",
                      "	Ballia", "Balrampur", "	Banda", "Barabanki", "Bareilly", "Basti", "Bhadohi", "Bijnor", "Budaun", "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", 
                      "Etah", "Etawah", "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur",
                      "Hapur (Panchsheel Nagar)", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj", "Kanpur Dehat", "Kanpur Nagar", "Kanshiram Nagar (Kasganj)", 
                      "Kaushambi", "Kushinagar (Padrauna)", "Lakhimpur - Kheri", "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau", "Meerut", 
                      "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh", "RaeBareli", "Rampur", "Saharanpur", "Sambhal (Bhim Nagar)", "Sant Kabir Nagar", 
                      "Shahjahanpur", "Shamali (Prabuddh Nagar)", "Shravasti", "Siddharth Nagar", "Sitapur", "Sonbhadra", "Sultanpur", "Unnao", "Varanasi"};
                    break;
                case "Uttarakhand": cities = new String[]{"Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag",
                      "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi"};
                    break;
                case "West Bengal": cities = new String[]{"Alipurduar", "Bankura", "Paschim Bardhaman", "Purba Bardhaman","Birbhum","Cooch Behar", "Darjeeling", "Uttar Dinajpur",
                    "Dakshin Dinajpur", "Hooghly", "Howrah", "Jalpaiguri", "Jhargram", "Kolkata", "Kalimpong", "Malda", "Paschim Medinipur", "Purba Medinipur", "Murshidabad",
                    "Nadia", "North 24 Parganas", "South 24 Parganas", "Purulia"};
                    break;
            }
        }

        return cities;
    }

}
