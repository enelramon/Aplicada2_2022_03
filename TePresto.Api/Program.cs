using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

var builder = WebApplication.CreateBuilder(args);
{
    builder.Services.AddDbContext<PrestoDbContext>(opt => opt.UseInMemoryDatabase("TodoList"));
    builder.Services.AddDatabaseDeveloperPageExceptionFilter();
    builder.Services.AddSingleton<ContextFactory>();
}

var app = builder.Build();

//EndPoints
app.MapGet("/", () => "Hello World!");

app.MapGet("/ocupaciones", async (PrestoDbContext db) =>
    await db.Ocupaciones.ToListAsync()
);

app.MapPost("/ocupaciones", async (Ocupaciones ocupacion, PrestoDbContext db) =>
{
    
    db.Add(ocupacion);
    await db.SaveChangesAsync();

    return Results.Created($"/ocupaciones/{ocupacion.OcupacionId}", ocupacion);
});

app.Run();

public class Ocupaciones
{
    [Key]
    public int OcupacionId { get; set; }
    public string? Descripcion { get; set; }
    public double? Sueldo { get; set; }
}
 

public class PrestoDbContext : DbContext
{ 
    public DbSet<Ocupaciones> Ocupaciones => Set<Ocupaciones>();
    
    public PrestoDbContext(DbContextOptions<PrestoDbContext> options)
        : base(options) { }
}

public class ContextFactory
{
    private readonly DbContextOptions<PrestoDbContext> _options;

    public ContextFactory(DbContextOptions<PrestoDbContext> options)
    {
        _options = options;
    }

    public PrestoDbContext CreateDbContext()
    {
        return new PrestoDbContext(_options);
    }
}


public class OcupacionesRepository
{
    private readonly ContextFactory _contextFactory;

    public OcupacionesRepository(ContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    } 

    public async Task<List<Ocupaciones>> GetList()
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.Ocupaciones.ToListAsync();
    }

    public async Task<Ocupaciones?> Find(int id)
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.Ocupaciones.FindAsync(id);
    }

    public async Task<Ocupaciones> Insert(Ocupaciones ocupacion)
    {
        using var context = _contextFactory.CreateDbContext();
        context.Add(ocupacion);
        await context.SaveChangesAsync();

        return ocupacion;
    }

    public async Task<Ocupaciones> Update(Ocupaciones ocupacion)
    {
        using var context = _contextFactory.CreateDbContext();
        context.Update(ocupacion);
        await context.SaveChangesAsync();

        return ocupacion;
    }

    public async Task<bool> Delete(int id)
    {
        using var context = _contextFactory.CreateDbContext();
        var ocupacion = await context.Ocupaciones.FindAsync(id);
        context.Remove(ocupacion!);
       
        return await context.SaveChangesAsync()>0;
    }    
}

// create a test for OcupacionesRepository using xUnit
public class OcupacionesRepositoryTest
{
    [Fact]
    public async Task GetList()
    {
        var options = new DbContextOptionsBuilder<PrestoDbContext>()
            .UseInMemoryDatabase("GetList")
            .Options;

        using (var context = new PrestoDbContext(options))
        {
            context.Add(new Ocupaciones { Descripcion = "Programador", Sueldo = 1000 });
            context.Add(new Ocupaciones { Descripcion = "Arquitecto", Sueldo = 2000 });
            context.Add(new Ocupaciones { Descripcion = "Diseñador", Sueldo = 3000 });
            context.SaveChanges();
        }

        using (var context = new PrestoDbContext(options))
        {
            var repository = new OcupacionesRepository(new ContextFactory(options));
            var list = await repository.GetList();

            Assert.Equal(3, list.Count);
        }
    }

    [Fact]
    public async Task Find()
    {
        var options = new DbContextOptionsBuilder<PrestoDbContext>()
            .UseInMemoryDatabase("Find")
            .Options;

        using (var context = new PrestoDbContext(options))
        {
            context.Add(new Ocupaciones { Descripcion = "Programador", Sueldo = 1000 });
            context.Add(new Ocupaciones { Descripcion = "Arquitecto", Sueldo = 2000 });
            context.Add(new Ocupaciones { Descripcion = "Diseñador", Sueldo = 3000 });
            context.SaveChanges();
        }

        using (var context = new PrestoDbContext(options))
        {
            var repository = new OcupacionesRepository(new ContextFactory(options));
            var ocupacion = await repository.Find(2);

            Assert.Equal("Arquitecto", ocupacion?.Descripcion);
        }
    }

    [Fact]
    public async Task Insert()
    {
        var options = new DbContextOptionsBuilder<PrestoDbContext>()
            .UseInMemoryDatabase("Insert")
            .Options;

        using (var context = new PrestoDbContext(options))
        {
            var repository = new OcupacionesRepository(new ContextFactory(options));
            var ocupacion = await repository.Insert(new Ocupaciones { Descripcion = "Programador", Sueldo = 1000 });

            Assert.Equal("Programador", ocupacion.Descripcion);
        }
    }

}

