f = open("female-names.txt")

names_count = {}

for line in f:
    if line:
        if line[0] in names_count:
            names_count[line[0]] += 1
        else:
            names_count[line[0]] = 1
f.close()

result = open("initials4redis.txt", "w+")

for i in names_count:
    result.write("SET " + i.upper() + " " + str(names_count[i]) + "\n")

result.close()