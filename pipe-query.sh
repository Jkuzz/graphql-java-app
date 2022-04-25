#!/bin/sh

echo "{
    kraje{
        name
    }
}" >> query-pipe
sleep 3
echo "{
    kraje{
        name
        id
    }
}
" >> query-pipe
sleep 3
echo "{
    obecById(id: 539210) {
        name
        okres {
            name
        }
        kraj {
            name
        }
        demographics {
            year
            migSaldo
        }
    }
}" >> query-pipe