import random


picked_names = []
emails = []
usernames = []
vid_tags = []
ratings = []
comments = []
vid_ids = []

fnames = []
lnames = []
with open("fnames.txt") as first:
    with open("lnames.txt") as last:
        for fname in first:
            fnames.append(fname)
        for lname in last:
            lnames.append(lname)  

for i in range(30):
    fname = random.choice(fnames)
    lname = random.choice(lnames)
    name = "{} {}".format(fname.strip(), lname.strip())
    while name in picked_names:
        fname = random.choice(fnames)
        lname = random.choice(lnames)
        name = "%s %s".format(fname.strip(), lname.strip())
    picked_names.append(name.replace("'", "\'"))

providers = ["@gmail.com", "@outlook.com", "@hotmail.com", "@sapo.pt", "@portugalmail.pt"]
for name in picked_names:
    emails.append(name.replace(" ", "_").lower() + str(random.randint(1950, 2020)) + random.choice(providers))
    usernames.append(name.replace(" ", "_").lower())

with open("ratings.txt") as f:
    ids = []

    for line in f:
        line = line.split(" ")
        id, value, user = line[0], line[1].strip(), random.choice(usernames)
        print("INSERT INTO rating (vid_id, user, value) VALUES ('{}', '{}', {});".format(id, user, value))