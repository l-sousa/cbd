import json
import csv

with open("disney_data_final.json") as m:
    m = json.load(m)
    with open("movies.txt", "w") as out:
        for movie in m:
            try:
                title = movie["title"]
                imdb = movie["imdb"]
                if imdb != 'N/A' and imdb is not None: imdb = float(imdb)
                else: continue
                time = movie["Running time (int)"]
                rt = movie["rotten_tomatoes"]
                if rt: rt = float(rt[:-1]) 
                else: continue
                    
                budget = movie["Budget (float)"]
                if not budget:
                    continue
                box_office = movie["Box office (float)"]
                directors = movie["Directed by"]
                producers = movie["Produced by"]
                
                if imdb > 10:
                    continue
                
            except KeyError:
                continue
            
            out.write(f"{title}$%&{time}$%&{imdb}$%&{rt}$%&{budget}$%&\
                {box_office}$%&{directors}$%&{producers}\n")


with open("imdb_comments.txt") as c:
    with open("comments.txt", "w") as out:
        for row in c:
            row = row.split(",", 1)[1].replace("'","")
            out.write(f'{row[1:-2]}\n')

